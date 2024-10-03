import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Appointment, AppointmentStatus } from '../models/appointment.interface';
import { Observable } from 'rxjs';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = `${base_url}/api/appointments`; // Asegúrate de que este endpoint sea correcto
  private apiUrlSlot = `${base_url}/api/slots`;

  private http = inject(HttpClient);

  constructor() { }

  // Método para crear un appointment
  createAppointment(appointment: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>(this.apiUrl, appointment);
  }

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.apiUrl);
  }

  getAppointmentById(id: string): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.apiUrl}/${id}`);
  }

  getAppointmentsByCustomerEmail(customerEmail: string): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/user/${customerEmail}`);
  }

  getUserAppointments(userId: string): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/user/${userId}`);
  }

  updateAppointment(id: string, appointment: Appointment): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.apiUrl}/${id}`, appointment);
  }

  updateAppointmentStatus(appointmentId: string, status: AppointmentStatus): Observable<any> {
    console.log('Status:', status);
    return this.http.put(`${this.apiUrl}/${appointmentId}`, { status: status });
  }

  deleteAppointment(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  loadAvailableSlots(selectedDate: string): Observable<string[]> { // Asegúrate de que LocalTime[] sea el tipo correcto
    return this.http.get<string[]>(`${this.apiUrlSlot}/available`, {
      params: { date: selectedDate },
    });
  }

  reserveSlots(date: string, time: string): Observable<any> {
    const slot = { date, time };
    return this.http.post(`${this.apiUrlSlot}/reserve`, slot);
  }

  // Método para verificar si el horario (slot) sigue disponible
  checkAvailability(date: string, time: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrlSlot}/check`, {
      params: { date, time }
    });
  }

  // Método para enviar la reseña al backend
  addReview(appointmentId: string, formData: FormData) {
    return this.http.post(`${this.apiUrl}/${appointmentId}/review`, formData);
  }

}
