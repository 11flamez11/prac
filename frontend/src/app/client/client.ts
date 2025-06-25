import { Component, OnInit } from '@angular/core';
import { ClientService, ClientDto } from './client.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-client',
  standalone: true,
  templateUrl: './client.html',
  styleUrls: ['./client.scss'],
  imports: [NgFor, NgIf, NgClass, FormsModule]
})
export class ClientComponent implements OnInit {
  clients: ClientDto[] = [];
  currentClient: ClientDto = { name: '', phone: '', email: '', address: '' };
  isEditing = false;

  message = '';
  isError = false;

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.loadClients();
  }

  loadClients() {
    this.clientService.getAllClients().subscribe(data => {
      this.clients = data;
    });
  }

  onSubmit() {
    if (this.isEditing && this.currentClient.id) {
      this.clientService.updateClient(this.currentClient.id, this.currentClient).subscribe({
        next: () => {
          this.loadClients();
          this.cancelEdit();
          this.showMessage('Клиент успешно обновлён!');
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    } else {
      this.clientService.createClient(this.currentClient).subscribe({
        next: () => {
          this.loadClients();
          this.resetForm();
          this.showMessage('Клиент успешно добавлен!');
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    }
  }

  editClient(client: ClientDto) {
    this.currentClient = { ...client };
    this.isEditing = true;
  }

  deleteClient(id: number) {
    if (confirm('Вы уверены, что хотите удалить этого клиента?')) {
      this.clientService.deleteClient(id).subscribe({
        next: () => {
          this.loadClients();
          this.showMessage('Клиент успешно удалён!');
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
    this.currentClient = { name: '', phone: '', email: '', address: '' };
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
