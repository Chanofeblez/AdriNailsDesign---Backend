/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/models/appointment.interface';
import { CustomerInterface } from 'src/app/models/customer.interface';
import { Servicio, ServicioVariant } from 'src/app/models/servicios.interface';
import { AppointmentService } from 'src/app/services/appointment.service';
import { CustomerService } from 'src/app/services/customer.service';
import { ServiciosService } from 'src/app/services/servicios.service';
import { VariantsService } from 'src/app/services/variants.service';

@Component({
  selector: 'app-booking-info',
  templateUrl: './booking-info.page.html',
  styleUrls: ['./booking-info.page.scss'],
})
export class BookingInfoPage implements OnInit {

  appointmentId: string | null;  // Para almacenar el ID de la cita
  appointment: Appointment;
  serviceDetails: Servicio;
  variants: ServicioVariant[] = [];  // Para almacenar los detalles de la cita
  formattedAppointmentDateTime: string;  // Declaración de la propiedad
  nombre: CustomerInterface;  // Declarar la propiedad nombre
  totalPrice: number = 0;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appointmentService = inject(AppointmentService);
  private variantsService = inject(VariantsService);
  private servicioService = inject(ServiciosService);
  private customerService = inject(CustomerService);

  constructor() { }

  ngOnInit() {
    // Obtener el parámetro de la URL
    this.route.paramMap.subscribe(params => {
      this.appointmentId = params.get('id');
      if (this.appointmentId) {
        this.loadAppointmentDetails(this.appointmentId);
      }
    });

  }

  // Método para cargar los detalles de la cita
  async loadAppointmentDetails(id: string) {
    try {
      // Obtener los detalles del appointment
      const appointment = await this.appointmentService.getAppointmentById(id).toPromise();

      if (appointment) {
        this.appointment = appointment;
        console.log("this.appointment:", this.appointment);
      } else {
        console.error("No se pudo obtener el appointment, es undefined.");
      }
      console.log("this.appointment:", this.appointment);

      // Formatear la fecha y hora del appointment
      this.formattedAppointmentDateTime = this.formatAppointmentDateTime(
        this.appointment.appointmentDate,
        this.appointment.appointmentTime
      );

      // Obtener el nombre usando await
      const nombreResult = await this.getNombre(this.appointment.customerEmail);
      console.log("nombreResult", nombreResult);

      if (nombreResult) {
        this.nombre = nombreResult;
        console.log("nombre", this.nombre);
      } else {
        console.error("No se pudo obtener el nombre.");
      }

      // Cargar los detalles del servicio y variantes en paralelo
      await Promise.all([
        this.loadServiceDetails(this.appointment.serviceName),
        this.loadServiceVariants(this.appointment.serviceVariantIds)
      ]).then(() => {
        // Después de que ambos métodos hayan resuelto, calcular el precio total
        this.calculateTotalPrice();
      });

      this.checkReview();

    } catch (error) {
      console.error('Error al cargar los detalles de la cita:', error);
    }
  }

  checkReview() {
    if (this.appointment.review === null) {
      // Si no hay review, crea un placeholder con todas las propiedades requeridas
      this.appointment.review = {
        id: 'placeholder-id', // Asigna un ID predeterminado para evitar problemas de tipos
        reviewText: 'No Review',
        rating: 0,
        customerPhoto: ''
      };
      console.log("this.appointment.review",this.appointment.review);
    }
  }

  // Método para formatear la fecha y hora
  private formatAppointmentDateTime(date: string, time: string): string {
    const dateObject = new Date(`${date}T${time}`);
    const options: Intl.DateTimeFormatOptions = {  // Especificamos el tipo correcto para las opciones
      weekday: 'short',  // Mon, Tue, etc.
      day: 'numeric',    // 12, 25, etc.
      month: 'short',    // Aug, Sep, etc.
      hour: 'numeric',   // 10
      minute: 'numeric', // 00
      hour12: true       // AM/PM
    };

    return dateObject.toLocaleString('en-US', options); // Usamos toLocaleString con las opciones
  }

  // Método asíncrono para obtener el cliente por su email
  async getNombre(customerEmail: string): Promise<CustomerInterface | void> {
    try {
      const customer = await this.customerService.getCustomerByEmail(customerEmail).toPromise();
      return customer;
    } catch (error) {
      console.error('Error al obtener el nombre del cliente', error);
    }
  }


  private loadServiceVariants(serviceVariantIds: string[]): Promise<void> {
    return new Promise((resolve, reject) => {
      this.variantsService.getVariantsByIds(serviceVariantIds).subscribe(
        (variants) => {
          this.variants = variants;
          console.log("this.variants:", this.variants);
          if (this.variants && this.variants.length > 0) {
            resolve(); // Resuelve la promesa cuando las variantes se han cargado
          } else {
            reject('No se encontraron variantes'); // Rechaza si no hay variantes
          }
        },
        (error) => {
          console.error('Error al cargar las variantes del servicio:', error);
          reject(error); // Rechaza la promesa si ocurre un error
        }
      );
    });
  }


  // Método para cargar los detalles del servicio por nombre
  async loadServiceDetails(serviceName: string): Promise<void> {
    try {
      const serviceDetails = await this.servicioService.getServiceByName(serviceName).toPromise();
      if (serviceDetails) {
        this.serviceDetails = serviceDetails;
        console.log("Service details", this.serviceDetails);
      } else {
        console.error('El servicio no fue encontrado.');
      }
    } catch (error) {
      console.error('Error al cargar los detalles del servicio:', error);
    }
  }

  // Método para calcular el total
  calculateTotalPrice() {
    console.log("calculateTotalPrice()");
    // Verifica si los detalles del servicio y las variantes están disponibles
    if (!this.serviceDetails) {
      console.error("serviceDetails no está definido");
      return;
    }

    if (!this.variants || this.variants.length === 0) {
      console.error("variants no están definidos o están vacíos");
      return;
    }

    // Precio del servicio principal
    const servicePrice = this.serviceDetails?.price || 0;
    console.log("servicePrice:", servicePrice);

    // Sumar los precios de todas las variantes
    const variantsPrice = this.variants.reduce((acc, variant) => acc + (variant?.price || 0), 0);
    console.log("variantsPrice:", variantsPrice);

    // Sumar el precio del servicio y las variantes
    this.totalPrice = servicePrice + variantsPrice;
    console.log("this.totalPrice:", this.totalPrice);

    // Mostrar el resultado en consola para depuración
    console.log("Total Price:", this.totalPrice);
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



  onBack() {
    this.router.navigate(['/appointments']);
  }

}
