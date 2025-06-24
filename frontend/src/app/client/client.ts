import { Component, OnInit } from '@angular/core';
import { ClientService, ClientDto } from './client.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-client',
  standalone: true,
  templateUrl: './client.html',
  styleUrls: ['./client.scss'],
  imports: [NgFor]
})
export class ClientComponent implements OnInit {
  clients: ClientDto[] = [];

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.clientService.getAllClients().subscribe(data => {
      this.clients = data;
    });
  }
}
