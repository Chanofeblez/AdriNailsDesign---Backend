import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ServicioVariant } from '../models/servicios.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class VariantsService {

  private apiUrl = `${base_url}/api/service-variants`;

  private http = inject(HttpClient);

  constructor() { }

  getVariantsByIds(variantIds: string[]): Observable<ServicioVariant[]> {
    return this.http.post<ServicioVariant[]>(`${this.apiUrl}/by-ids`, variantIds);
  }
}
