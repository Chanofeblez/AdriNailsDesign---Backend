/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, inject, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { UtilService } from 'src/app/services/util.service';
import { WriteReviewsPage } from '../write-reviews/write-reviews.page';
import { AppointmentService } from 'src/app/services/appointment.service';
import { Appointment, AppointmentStatus } from 'src/app/models/appointment.interface';
import { CustomerService } from 'src/app/services/customer.service';
import { CustomerInterface } from 'src/app/models/customer.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.page.html',
  styleUrls: ['./appointments.page.scss'],
})
export class AppointmentsPage implements OnInit {

  appointments : Appointment[] = [];
  completedAppointments : Appointment[] = [];
  customerNames: { [key: string]: string } = {};  // Objeto para almacenar los nombres de los clientes
  customers: CustomerInterface[] = [];



  private appointmentService = inject(AppointmentService);
  private customerService = inject(CustomerService);
  public util = inject(UtilService);
  private modalController = inject(ModalController);
  private router = inject(Router);

  currentSegment: any = 'upcoming';
  constructor(

  ) { }

  ngOnInit() {
    this.loadAppointments();
    this.loadCustomers();
  }

  // Método para cargar los appointments y los nombres de los clientes
loadAppointments() {
  this.appointments = [];
  this.completedAppointments = [];

  this.appointmentService.getAllAppointments().subscribe(
    async (appointments: Appointment[]) => {
      const currentDate = new Date();

      // Primero actualizamos el estado de los appointments pasados a 'COMPLETED'
      for (const appointment of appointments) {
        const appointmentDate = new Date(`${appointment.appointmentDate}T${appointment.appointmentTime}`);

        // Si la fecha del appointment ya pasó y no está completado ni cancelado, actualizamos el status a 'COMPLETED'
        if (appointmentDate < currentDate && appointment.status !== AppointmentStatus.COMPLETED && appointment.status !== AppointmentStatus.CANCELED) {
          appointment.status = AppointmentStatus.COMPLETED;
          await this.updateAppointmentStatus(appointment); // Método para actualizar el appointment
        }
      }

      // Luego filtramos los appointments que no están completados ni cancelados
      const filteredAppointments = appointments.filter(appointment =>
        appointment.status !== AppointmentStatus.COMPLETED && appointment.status !== AppointmentStatus.CANCELED
      );

      // Ordenar los appointments filtrados por fecha (appointmentDate)
      const sortedAppointments = filteredAppointments.sort((a, b) => {
        const dateA = new Date(`${a.appointmentDate}T${a.appointmentTime}`).getTime();
        const dateB = new Date(`${b.appointmentDate}T${b.appointmentTime}`).getTime();
        return dateA - dateB;
      });

      // Filtrar y ordenar los appointments con status 'COMPLETED'
      const completedAppointments = appointments.filter(appointment => appointment.status === 'COMPLETED');

      // Ordenar los appointments completados por fecha más reciente a más antigua
      this.completedAppointments = completedAppointments.sort((a, b) => {
        return new Date(b.appointmentDate).getTime() - new Date(a.appointmentDate).getTime();
      });


      this.appointments = sortedAppointments;
      console.log("this.appointments:", this.appointments);
      console.log("Completed Appointments:", this.completedAppointments);

      // Obtener el nombre de los clientes por email y almacenarlos en el objeto customerNames
      for (const appointment of appointments) {
        if (appointment.customerEmail) {
          const nombre = await this.getNombre(appointment.customerEmail);
          this.customerNames[appointment.customerEmail] = nombre || 'Desconocido'; // Asignar un valor predeterminado si getNombre() devuelve void
        }
      }
    },
    (error) => {
      console.error('Error al cargar los appointments', error);
    }
  );
}


  // Método para actualizar el status del appointment a 'COMPLETED'
private updateAppointmentStatus(appointment: Appointment): Promise<void> {
  if (!appointment.id) {
    console.error(`Appointment ID is undefined. No se puede actualizar el status.`);
    return Promise.reject('Appointment ID is undefined.');
  }

  return this.appointmentService.updateAppointmentStatus(appointment.id, AppointmentStatus.COMPLETED).toPromise()
    .then(() => {
      console.log(`Appointment ${appointment.id} actualizado a COMPLETED.`);
    })
    .catch((error) => {
      console.error(`Error al actualizar appointment ${appointment.id}:`, error);
    });
}


  // Método asíncrono para obtener el nombre del cliente por su email
  async getNombre(customerEmail: string): Promise<string | void> {
    try {
      const customer = await this.customerService.getCustomerByEmail(customerEmail).toPromise();
      return customer?.name;
    } catch (error) {
      console.error('Error al obtener el nombre del cliente', error);
    }
  }


  async openReviews() {
    const modal = await this.modalController.create({
      component: WriteReviewsPage,
      cssClass: 'payment-modal'
    });
    await modal.present();
  }

  formatAppointmentTime(time: string): string {
    const [hours, minutes] = time.split(':').map(Number); // Dividimos la hora y los minutos
    const suffix = hours >= 12 ? 'PM' : 'AM'; // Determinamos si es AM o PM
    const hours12 = hours % 12 || 12; // Convertimos el formato de 24 horas a 12 horas
    return `${hours12}:${minutes.toString().padStart(2, '0')}${suffix}`; // Formateamos la salida
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'PENDING':
        return 'warning';
      case 'CONFIRMED':
        return 'secondary';
      case 'COMPLETED':
        return 'success';
      case 'CANCELED':
        return 'danger';
      default:
        return 'primary';
    }
  }

  loadCustomers() {
    this.customerService.getAllCustomers().subscribe(
      (customers: CustomerInterface[]) => {
        this.customers = customers;
      },
      (error) => {
        console.error('Error al cargar los clientes', error);
      }
    );
  }

  getColor(customer: CustomerInterface): string {
    const colors = ['#61BFBE', '#E0C4F7', '#FCC4B3', '#FFD700', '#FFB6C1', '#87CEFA']; // Puedes agregar más colores si lo deseas
    const index = this.customers.indexOf(customer) % colors.length;
    return colors[index];
  }


  onInfo(appointment: Appointment) {
    console.log("appointment", appointment);
    console.log("appointment.id", appointment.id);
    this.router.navigate([`/booking-info`, appointment.id]);  // Navegar a booking-info pasando el ID
  }

  goToCustomerInfo(customer: CustomerInterface) {
    this.router.navigate(['/customer-info', customer.id, customer.email]);
  }


}
