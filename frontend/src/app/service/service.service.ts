import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ServiceDto {
  id?: number;
  name: string;
  description: string;
  price: undefined;
}

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  private apiUrl = 'http://localhost:8080/services';

  constructor(private http: HttpClient) {}

  getAllServices(): Observable<ServiceDto[]> {
    return this.http.get<ServiceDto[]>(this.apiUrl);
  }

  createService(service: ServiceDto): Observable<ServiceDto> {
    return this.http.post<ServiceDto>(this.apiUrl, service);
  }

  updateService(id: number, service: ServiceDto): Observable<ServiceDto> {
    return this.http.put<ServiceDto>(`${this.apiUrl}/${id}`, service);
  }

  deleteService(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
