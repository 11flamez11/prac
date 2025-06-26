import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CarDto {
  id?: number;
  vin?: string;
  markModel?: string;
  yearManufacture?: number;
  plateNum?: string;
  lastServiceDate?: string;
  clientId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private baseUrl = 'http://localhost:8080/cars';

  constructor(private http: HttpClient) {}

  getAllCars(): Observable<CarDto[]> {
    return this.http.get<CarDto[]>(this.baseUrl);
  }

  getCarById(id: number): Observable<CarDto> {
    return this.http.get<CarDto>(`${this.baseUrl}/${id}`);
  }

  createCar(car: CarDto): Observable<CarDto> {
    return this.http.post<CarDto>(this.baseUrl, car);
  }

  updateCar(id: number, car: CarDto): Observable<CarDto> {
    return this.http.put<CarDto>(`${this.baseUrl}/${id}`, car);
  }

  deleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchCars(params: {
    carId?: number;
    vin?: string;
    markModel?: string;
    yearManufacture?: number;
    plateNum?: string;
    clientId?: number;
    lastServiceDate?: string;
  }): Observable<CarDto[]> {
    const queryParams = new URLSearchParams();
    if (params.carId) queryParams.append('carId', params.carId.toString());
    if (params.vin) queryParams.append('vin', params.vin);
    if (params.markModel) queryParams.append('markModel', params.markModel);
    if (params.yearManufacture) queryParams.append('yearManufacture', params.yearManufacture.toString());
    if (params.plateNum) queryParams.append('plateNum', params.plateNum);
    if (params.clientId) queryParams.append('clientId', params.clientId.toString());
    if (params.lastServiceDate) queryParams.append('lastServiceDate', params.lastServiceDate);

    const queryString = queryParams.toString();
    const url = queryString ? `${this.baseUrl}/search?${queryString}` : this.baseUrl;

    return this.http.get<CarDto[]>(url);
  }
}
