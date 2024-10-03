import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Servicio } from '../models/servicios.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  private apiUrl = `${base_url}/api/services`;

  private http = inject(HttpClient);

  constructor() { }

  getServiceByName(serviceName: string): Observable<Servicio> {
    console.log("serviceName", serviceName);
    const encodedServiceName = encodeURIComponent(serviceName);
    console.log("Encoded serviceName", encodedServiceName);
    return this.http.get<Servicio>(`${this.apiUrl}/by-name?name=${encodedServiceName}`);
  }
}
