import { Routes } from '@angular/router';
import { ClientComponent } from './client/client';
import { OrderComponent } from './order/order';
import { ServiceComponent } from './service/service';
import { CarComponent } from './car/car';

export const routes: Routes = [
  { path: 'clients', component: ClientComponent },
  { path: 'orders', component: OrderComponent },
  { path: 'services', component: ServiceComponent },
  { path: 'cars', component: CarComponent },
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
];
