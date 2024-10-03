export interface Appointment {
  id?: string;
  customerEmail: string; // El email del cliente, en lugar del objeto Customer
  serviceName: string; // ID del servicio, en lugar del objeto Servicio
  serviceVariantIds: string[]; // Lista de IDs de las variantes de servicio, en lugar de objetos ServicioVariant
  appointmentDate: string; // Fecha de la cita en formato 'yyyy-MM-dd'
  appointmentTime: string; // Hora de la cita en formato 'HH:mm:ss'
  totalCost: number; // Costo total de la cita
  status: AppointmentStatus; // Estado de la cita
  imagePath: string;
  review?: Review; // El review, si está presente
}

export interface Review {
  id: string;
  rating: number;
  reviewText: string;
  customerPhoto: string;
}

export enum AppointmentStatus {
  PENDING   = 'PENDING',
  UPCOMING  = 'UPCOMING',
  CANCELED = 'CANCELED',
  CONFIRMED = 'CONFIRMED',
  COMPLETED = 'COMPLETED'
}
