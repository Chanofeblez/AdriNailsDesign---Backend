import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiUrl = `${base_url}/api/courses`;

  private http = inject(HttpClient);

  constructor() {}

  createCourse(courseData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, courseData);
  }
}
