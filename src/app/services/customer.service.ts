import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CustomerInterface } from '../models/customer.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiUrl = `${base_url}/api/auth/customers`;  // La URL base de tu API

  private http = inject(HttpClient);

  constructor() { }

  // Método para obtener el cliente por su ID
  getCustomerById(customerId: string): Observable<CustomerInterface> {
    return this.http.get<CustomerInterface>(`${this.apiUrl}/${customerId}`);
  }

  // Método para obtener el cliente por email
  getCustomerByEmail(customerEmail: string): Observable<CustomerInterface> {
    return this.http.get<CustomerInterface>(`${this.apiUrl}/by-email?email=${customerEmail}`);
  }

  getAllCustomers(): Observable<CustomerInterface[]> {
    return this.http.get<CustomerInterface[]>(`${this.apiUrl}`);
  }

}
