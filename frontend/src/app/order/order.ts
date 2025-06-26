import { Component, OnInit } from '@angular/core';
import { OrderService, OrderDto } from './order.service';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-order',
  standalone: true,
  templateUrl: './order.html',
  styleUrls: ['./order.scss'],
  imports: [NgFor, NgIf, NgClass, FormsModule]
})
export class OrderComponent implements OnInit {
  orders: OrderDto[] = [];
  currentOrder: Partial<OrderDto> = { carId: 0, serviceId: 0, orderDate: '', completionDate: '', status: '', totalCost: 0 };
  isEditing = false;

  searchCriteria: {
    orderDate?: string;
    completionDate?: string;
    status?: string;
    orderId?: number;
  } = {};

  message = '';
  isError = false;

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getAllOrders().subscribe(data => {
      this.orders = data.sort((a, b) => (a.id ?? 0) - (b.id ?? 0));
    });
  }

  onSubmit() {
    if (this.isEditing && this.currentOrder.id) {
      this.orderService.updateOrder(this.currentOrder.id, this.currentOrder as OrderDto).subscribe({
        next: () => {
          this.loadOrders();
          this.cancelEdit();
          this.showMessage('Заказ успешно обновлён!');
        },
        error: (error) => this.handleError(error)
      });
    } else {
      const { id, ...newOrder } = this.currentOrder; // Убираем id, если вдруг оно осталось
      this.orderService.createOrder(newOrder as OrderDto).subscribe({
        next: () => {
          this.loadOrders();
          this.resetForm();
          this.showMessage('Заказ успешно добавлен!');
        },
        error: (error) => this.handleError(error)
      });
    }
  }

  editOrder(order: OrderDto) {
    this.currentOrder = { ...order };
    this.isEditing = true;
  }

  deleteOrder(id?: number) {
    if (id === undefined) return;

    if (confirm('Вы уверены, что хотите удалить этот заказ?')) {
      this.orderService.deleteOrder(id).subscribe({
        next: () => {
          this.loadOrders();
          this.showMessage('Заказ успешно удалён!');
        },
        error: (error) => this.handleError(error)
      });
    }
  }

searchOrders() {
  this.orderService.searchOrders(this.searchCriteria).subscribe({
    next: data => {
      this.orders = data;
      this.showMessage(`Найдено заказов: ${data.length}`);
    },
    error: error => this.handleError(error)
  });
}

  resetSearch() {
    this.searchCriteria = {};
    this.loadOrders();
  }


  cancelEdit() {
    this.resetForm();
    this.isEditing = false;
  }

  resetForm() {
    this.currentOrder = { carId: 0, serviceId: 0, orderDate: '', completionDate: '', status: '', totalCost: 0 };
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
