import { Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TrackingService } from '../../services/tracking.service';
import { TrackingResponse } from '../../models/envio.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  private readonly trackingService = inject(TrackingService);
  private readonly fb = inject(FormBuilder);

  searchCode = signal<string>('ENV-1001');
  isSearching = signal<boolean>(false);
  trackingResult = signal<TrackingResponse | null>(null);
  searchError = signal<string | null>(null);

  activeFaq = signal<number | null>(0);

  contactForm = this.fb.group({
    nombre: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    telefono: ['', [Validators.required, Validators.pattern(/^[0-9+ ]{7,15}$/)]],
    asunto: ['Cotización de Envío Empresarial', Validators.required],
    mensaje: ['', [Validators.required, Validators.minLength(10)]]
  });
  contactSubmitted = signal<boolean>(false);
  isSendingContact = signal<boolean>(false);
  contactTicket = signal<string>('RHL-TK-8492');

  agencias = [
    {
      ciudad: 'Lima - Miraflores',
      sede: 'Sede Principal Corporativa',
      direccion: 'Av. José Pardo 840, Miraflores',
      horario: 'Lun - Sáb: 08:00 - 20:00',
      telefono: '(01) 480-9900',
      imagen: 'assets/images/frontis.jpg',
      badge: 'Hub Central'
    },
    {
      ciudad: 'Arequipa',
      sede: 'Agencia Regional Sur',
      direccion: 'Av. Ejército 710, Yanahuara',
      horario: 'Lun - Sáb: 08:30 - 19:30',
      telefono: '(054) 220-410',
      imagen: 'assets/images/local por fuera.jpg',
      badge: 'Hub Sur'
    },
    {
      ciudad: 'Trujillo',
      sede: 'Agencia Regional Norte',
      direccion: 'Av. España 1450, Centro',
      horario: 'Lun - Sáb: 08:30 - 19:00',
      telefono: '(044) 310-850',
      imagen: 'assets/images/oficina por dentro.jpg',
      badge: 'Hub Norte'
    },
    {
      ciudad: 'Cusco',
      sede: 'Agencia Imperial',
      direccion: 'Av. de la Cultura 2180, Wanchaq',
      horario: 'Lun - Vie: 09:00 - 18:00',
      telefono: '(084) 290-700',
      imagen: 'assets/images/cliente.jpg',
      badge: 'Agencia'
    }
  ];

  faqs = [
    {
      q: '¿Cómo puedo rastrear mi encomienda en RHL Express?',
      a: 'Solo debes ingresar el código de rastreo (ejemplo: ENV-1001, ENV-1002) en la barra de búsqueda de nuestra página principal. Obtendrás información en tiempo real del progreso, ubicación exacta e historial detallado de tu paquete.'
    },
    {
      q: '¿Cuáles son los tiempos de entrega promedio a nivel nacional?',
      a: 'Para envíos interprovinciales principales (Lima, Arequipa, Trujillo, Cusco) el tiempo de entrega es de 24 a 48 horas. Para zonas especiales o repartos rurales, de 48 a 72 horas.'
    },
    {
      q: '¿Qué dimensiones y pesos máximos transporta RHL Express?',
      a: 'Manejamos desde sobres documentarios de 0.5 Kg hasta carga paletizada y contenedores completos sin límite de tonelaje a través de nuestra flota pesada de camiones Mercedes-Benz.'
    },
    {
      q: '¿Ofrecen servicio de recojo a domicilio para empresas?',
      a: 'Sí, contamos con recojo programado para clientes corporativos y personas naturales con nuestra flota de furgones Express. Contáctanos a través del formulario o nuestra central telefónica.'
    }
  ];

  buscarTracking(codigoAConsultar?: string): void {
    const code = (codigoAConsultar || this.searchCode()).trim().toUpperCase();
    if (!code) return;

    this.searchCode.set(code);
    this.isSearching.set(true);
    this.searchError.set(null);

    this.trackingService.getTracking(code).subscribe({
      next: (res) => {
        this.trackingResult.set(res);
        this.isSearching.set(false);
        this.scrollHaciaResultado();
      },
      error: () => {
        this.trackingResult.set(null);
        this.searchError.set(`No se encontró ningún envío registrado con el código "${code}". Verifica el número o prueba con ENV-1001.`);
        this.isSearching.set(false);
        this.scrollHaciaResultado();
      }
    });
  }

  private scrollHaciaResultado(): void {
    setTimeout(() => {
      const elem = document.getElementById('resultado-rastreo');
      if (elem) {
        elem.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    }, 120);
  }

  toggleFaq(index: number): void {
    this.activeFaq.update((curr) => (curr === index ? null : index));
  }

  irATrabajaConNosotros(): void {
    this.contactForm.patchValue({
      asunto: 'Bolsa de Trabajo / Trabaja con Nosotros'
    });
    const contactoElem = document.getElementById('contacto');
    if (contactoElem) {
      contactoElem.scrollIntoView({ behavior: 'smooth' });
    }
  }

  enviarContacto(): void {
    if (this.contactForm.invalid) {
      this.contactForm.markAllAsTouched();
      return;
    }

    this.isSendingContact.set(true);
    const ticketId = 'RHL-TK-' + Math.floor(1000 + Math.random() * 9000);
    this.contactTicket.set(ticketId);

    setTimeout(() => {
      this.isSendingContact.set(false);
      this.contactSubmitted.set(true);
    }, 500);
  }

  nuevaConsulta(): void {
    this.contactSubmitted.set(false);
    this.contactForm.reset({
      asunto: 'Cotización de Envío Empresarial'
    });
  }

  getStepProgress(estado: string): number {
    switch (estado) {
      case 'REGISTRADO': return 25;
      case 'EN_TRANSITO': return 50;
      case 'EN_SUCURSAL': return 75;
      case 'ENTREGADO': return 100;
      case 'CANCELADO': return 0;
      default: return 25;
    }
  }
}
