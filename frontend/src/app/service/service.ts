import { Component, OnInit } from '@angular/core';
import { ServiceDto, ServiceService } from './service.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-service',
  standalone: true,
  templateUrl: './service.html',
  styleUrls: ['./service.scss'],
  imports: [NgFor, NgIf, NgClass, FormsModule]
})
export class ServiceComponent implements OnInit {

  services: ServiceDto[] = [];
  currentService: ServiceDto = { name: '', description: '', price: 0 };
  isEditing = false;

  message = '';
  isError = false;

  constructor(private serviceService: ServiceService) {}

  ngOnInit() {
    this.loadServices();
  }

loadServices() {
  this.serviceService.getAllServices().subscribe(data => {
    this.services = data.sort((a, b) => (a.id! - b.id!));
  });
}
  onSubmit() {
    if (this.isEditing && this.currentService.id) {
      this.serviceService.updateService(this.currentService.id, this.currentService).subscribe({
        next: () => {
          this.loadServices();
          this.cancelEdit();
          this.showMessage('Услуга успешно обновлена!');
        },
        error: (error) => this.handleError(error)
      });
    } else {
      this.serviceService.createService(this.currentService).subscribe({
        next: () => {
          this.loadServices();
          this.resetForm();
          this.showMessage('Услуга успешно добавлена!');
        },
        error: (error) => this.handleError(error)
      });
    }
  }

  editService(service: ServiceDto) {
    this.currentService = { ...service };
    this.isEditing = true;
  }

  deleteService(id: number) {
    if (confirm('Вы уверены, что хотите удалить эту услугу?')) {
      this.serviceService.deleteService(id).subscribe({
        next: () => {
          this.loadServices();
          this.showMessage('Услуга успешно удалена!');
        },
        error: (error) => this.handleError(error)
      });
    }
  }

  cancelEdit() {
    this.resetForm();
    this.isEditing = false;
  }

  resetForm() {
    this.currentService = { name: '', description: '', price: 0 };
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
