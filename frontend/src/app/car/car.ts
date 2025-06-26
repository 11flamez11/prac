import { Component, OnInit } from '@angular/core';
import { CarService, CarDto } from './car.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientService, ClientDto} from '../client/client.service';

@Component({
  selector: 'app-car',
  standalone: true,
  templateUrl: './car.html',
  styleUrls: ['./car.scss'],
  imports: [NgFor, NgIf, NgClass, FormsModule]
})
export class CarComponent implements OnInit {
  cars: CarDto[] = [];
  currentCar: CarDto = {
    vin: '',
    markModel: '',
    yearManufacture: new Date().getFullYear(),
    plateNum: '',
    lastServiceDate: '',
    clientId: undefined
  };
  isEditing = false;
  showClientModal = false;
  client:ClientDto={ name: '', phone: '', email: '', address: '' };

  message = '';
  isError = false;

  searchCriteria: {
      markModel?: string;
      plateNum?: string;
      clientId?: number;
      lastServiceDate?: string;
      carId?: number;
    } = {};

  constructor(private carService: CarService, private clientService: ClientService) {}

  ngOnInit() {
    this.loadCars();
  }

  loadCars() {
    this.carService.getAllCars().subscribe(data => {
      this.cars = data;
    });
  }

  onSubmit() {
    if (this.isEditing && this.currentCar.id) {
      this.carService.updateCar(this.currentCar.id, this.currentCar).subscribe({
        next: () => {
          this.loadCars();
          this.cancelEdit();
          this.showMessage('Машина успешно обновлена!');
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    } else {
      this.carService.createCar(this.currentCar).subscribe({
        next: () => {
          this.loadCars();
          this.resetForm();
          this.showMessage('Машина успешно добавлена!');
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    }
  }

  searchCars() {
      this.carService.searchCars(this.searchCriteria).subscribe({
        next: data => {
          this.cars = data;
          this.showMessage(`Найдено машин: ${data.length}`);
        },
        error: error => this.handleError(error)
      });
    }

    resetSearch() {
      this.searchCriteria = {};
      this.loadCars();
    }

  editCar(car: CarDto) {
    this.currentCar = { ...car };
    this.isEditing = true;
  }

  deleteCar(id: number) {
    if (confirm('Вы уверены, что хотите удалить эту машину?')) {
      this.carService.deleteCar(id).subscribe({
        next: () => {
          this.loadCars();
          this.showMessage('Машина успешно удалена!');
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    }
  }

  cancelEdit() {
    this.resetForm();
    this.isEditing = false;
  }

  resetForm() {
    this.currentCar = {
      vin: '',
      markModel: '',
      yearManufacture: new Date().getFullYear(),
      plateNum: '',
      lastServiceDate: '',
      clientId: undefined
    };
  }

  onClientIdClick(clientId?: number) {
    if (!clientId) return;

    this.clientService.getClientById(clientId).subscribe({
      next: (client) => {
        this.client = client;
        this.showClientModal = true;
      },
      error: (error) => this.handleError(error)
    });
  }

  closeClientModal() {
    this.showClientModal = false;
  }

  handleError(error: any) {
    if (error.status === 400 && error.error) {
      this.showMessage('Ошибка: ' + JSON.stringify(error.error), true);
    }
    else if (error.status === 409 && error.error) {
                   this.showMessage('Ошибка: ' + JSON.stringify(error.error), true);
    }
    else {
      this.showMessage('Произошла ошибка. Пожалуйста, попробуйте снова.', true);
    }
  }

showMessage(message: string, isError: boolean = false) {
  this.message = message;
  this.isError = isError;

  setTimeout(() => {
    this.message = '';
  }, 3000);
}
}
