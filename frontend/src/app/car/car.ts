import { Component, OnInit } from '@angular/core';
import { CarService, CarDto } from './car.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

  message = '';
  isError = false;

  constructor(private carService: CarService) {}

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

  handleError(error: any) {
    if (error.status === 400 && error.error) {
      this.showMessage('Ошибка: ' + JSON.stringify(error.error), true);
    } else {
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
