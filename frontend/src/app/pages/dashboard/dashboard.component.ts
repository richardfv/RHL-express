import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EnvioService } from '../../services/envio.service';
import { Envio, EnvioCrearRequest, EstadoEnvio } from '../../models/envio.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  readonly authService = inject(AuthService);
  private readonly envioService = inject(EnvioService);
  private readonly fb = inject(FormBuilder);

  envios = signal<Envio[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  searchTerm = signal<string>('');
  filterEstado = signal<string>('TODOS');

  showCreateModal = signal<boolean>(false);
  showEditModal = signal<boolean>(false);
  showDeleteModal = signal<boolean>(false);
  selectedEnvio = signal<Envio | null>(null);

  createForm = this.fb.group({
    remitente: ['', [Validators.required, Validators.minLength(3)]],
    dniRemitente: ['', [Validators.required, Validators.pattern(/^[0-9]{8}$/)]],
    destinatario: ['', [Validators.required, Validators.minLength(3)]],
    ciudadDestino: ['Lima', Validators.required],
    direccionDestino: ['', [Validators.required, Validators.minLength(5)]],
    peso: [null as number | null, [Validators.required, Validators.min(0.1)]],
    costo: [null as number | null, [Validators.required, Validators.min(1.0)]],
    categoriaContenido: ['Documentos y Trámites', Validators.required],
    descripcionOtro: ['']
  });

  editForm = this.fb.group({
    estado: ['REGISTRADO' as EstadoEnvio, Validators.required],
    notas: ['']
  });

  isAdmin = computed(() => this.authService.userRole() === 'ADMIN');
  totalEnvios = computed(() => this.envios().length);
  enTransito = computed(() => this.envios().filter(e => e.estado === 'EN_TRANSITO').length);
  entregados = computed(() => this.envios().filter(e => e.estado === 'ENTREGADO').length);
  pendientes = computed(() => this.envios().filter(e => e.estado !== 'ENTREGADO').length);
  recaudacion = computed(() => this.envios().reduce((acc, curr) => acc + (curr.costo || 0), 0));

  enviosFiltrados = computed(() => {
    const term = this.searchTerm().toLowerCase().trim();
    const estado = this.filterEstado();

    return this.envios().filter((envio) => {
      const matchesTerm =
        !term ||
        (envio.codigoTracking && envio.codigoTracking.toLowerCase().includes(term)) ||
        (envio.remitente && envio.remitente.toLowerCase().includes(term)) ||
        (envio.destinatario && envio.destinatario.toLowerCase().includes(term)) ||
        (envio.ciudadDestino && envio.ciudadDestino.toLowerCase().includes(term));

      const matchesEstado = estado === 'TODOS' || envio.estado === estado;

      return matchesTerm && matchesEstado;
    });
  });

  ngOnInit(): void {
    this.cargarEnvios();
  }

  cargarEnvios(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.envioService.getEnvios().subscribe({
      next: (data) => {
        this.envios.set(data || []);
        this.isLoading.set(false);
      },
      error: () => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al cargar la lista de envíos desde el Backend Gateway (8080).');
      }
    });
  }

  abrirCrearModal(): void {
    this.createForm.reset({
      remitente: '',
      dniRemitente: '',
      destinatario: '',
      ciudadDestino: 'Lima',
      direccionDestino: '',
      peso: null,
      costo: null,
      categoriaContenido: 'Documentos y Trámites',
      descripcionOtro: ''
    });
    this.showCreateModal.set(true);
  }

  guardarNuevoEnvio(): void {
    if (this.createForm.invalid) {
      this.createForm.markAllAsTouched();
      return;
    }

    const val = this.createForm.value;
    if (val.categoriaContenido === 'OTRO' && !val.descripcionOtro?.trim()) {
      alert('Por favor especifica la descripción del contenido en el campo habilitado.');
      return;
    }

    const descFinal = val.categoriaContenido === 'OTRO'
      ? val.descripcionOtro?.trim()
      : val.categoriaContenido;

    const nuevo: EnvioCrearRequest = {
      remitente: val.remitente!,
      dniRemitente: val.dniRemitente!,
      destinatario: val.destinatario!,
      direccionDestino: val.direccionDestino!,
      ciudadDestino: val.ciudadDestino!,
      peso: Number(val.peso),
      costo: Number(val.costo),
      descripcion: descFinal || 'Encomienda express'
    };

    this.envioService.createEnvio(nuevo).subscribe({
      next: (creado) => {
        this.envios.update((curr) => [creado, ...curr]);
        this.showCreateModal.set(false);
      },
      error: () => {
        alert('Error al registrar el envío en el servidor.');
      }
    });
  }

  abrirEditarModal(envio: Envio): void {
    this.selectedEnvio.set(envio);
    this.editForm.patchValue({
      estado: envio.estado,
      notas: ''
    });
    this.showEditModal.set(true);
  }

  guardarEdicion(): void {
    const selected = this.selectedEnvio();
    if (!selected || !selected.id) return;

    const val = this.editForm.value;
    const nuevoEstado = val.estado as string;

    this.envioService.updateEstado(selected.id, nuevoEstado).subscribe({
      next: (actualizado) => {
        this.envios.update((curr) =>
          curr.map((item) => (item.id === selected.id ? { ...item, estado: actualizado.estado } : item))
        );
        this.showEditModal.set(false);
      },
      error: () => {
        alert('Error al actualizar el estado de la encomienda.');
      }
    });
  }

  abrirEliminarModal(envio: Envio): void {
    if (!this.isAdmin()) return;
    this.selectedEnvio.set(envio);
    this.showDeleteModal.set(true);
  }

  confirmarEliminar(): void {
    if (!this.isAdmin()) return;
    const selected = this.selectedEnvio();
    if (!selected || !selected.id) return;

    this.envioService.deleteEnvio(selected.id).subscribe({
      next: () => {
        this.envios.update((curr) => curr.filter((item) => item.id !== selected.id));
        this.showDeleteModal.set(false);
        this.selectedEnvio.set(null);
      },
      error: () => {
        alert('Error al anular el envío en el servidor.');
      }
    });
  }
}
