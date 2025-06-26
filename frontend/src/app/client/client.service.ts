import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ClientDto {
  id?: number;
  name: string;
  phone?: string;
  email?: string;
  address?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private baseUrl = 'http://localhost:8080/clients';

  constructor(private http: HttpClient) {}

  getAllClients(): Observable<ClientDto[]> {
    return this.http.get<ClientDto[]>(this.baseUrl);
  }

  getClientById(id: number): Observable<ClientDto> {
    return this.http.get<ClientDto>(`${this.baseUrl}/${id}`);
  }

  createClient(client: ClientDto): Observable<ClientDto> {
    return this.http.post<ClientDto>(this.baseUrl, client);
  }

  updateClient(id: number, client: ClientDto): Observable<ClientDto> {
    return this.http.put<ClientDto>(`${this.baseUrl}/${id}`, client);
  }

  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  searchClients(params: { name?: string; phone?: string; clientId?: number }): Observable<ClientDto[]> {
    const queryParams = new URLSearchParams();
    if (params.name) queryParams.append('name', params.name);
    if (params.phone) queryParams.append('phone', params.phone);
    if (params.clientId !== undefined && params.clientId !== null) queryParams.append('clientId', params.clientId.toString());
    return this.http.get<ClientDto[]>(`${this.baseUrl}/search?${queryParams.toString()}`);
  }
}
