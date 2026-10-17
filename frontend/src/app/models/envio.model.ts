export type EstadoEnvio = 'REGISTRADO' | 'EN_TRANSITO' | 'EN_SUCURSAL' | 'ENTREGADO' | 'CANCELADO';

export interface Envio {
  id?: number;
  codigoTracking: string;
  remitente: string;
  dniRemitente: string;
  destinatario: string;
  direccionDestino: string;
  ciudadDestino: string;
  peso: number;
  costo: number;
  estado: EstadoEnvio;
  descripcion?: string;
  fechaRegistro?: string;
}

export interface EnvioCrearRequest {
  remitente: string;
  dniRemitente: string;
  destinatario: string;
  direccionDestino: string;
  ciudadDestino: string;
  peso: number;
  costo: number;
  descripcion?: string;
}

export interface HistorialTracking {
  id?: number;
  codigoTracking?: string;
  estado: EstadoEnvio;
  ubicacion: string;
  comentario: string;
  fechaRegistro: string;
}

export interface TrackingResponse {
  codigoTracking: string;
  estadoActual: EstadoEnvio;
  remitente: string;
  destinatario: string;
  ciudadDestino: string;
  historial: HistorialTracking[];
  observacionResiliencia?: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  rol: string;
}

export interface ContactoMensaje {
  nombre: string;
  email: string;
  telefono: string;
  asunto: string;
  mensaje: string;
}
