import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-client',
  standalone: true,
  templateUrl: './client.html',
  styleUrls: ['./client.scss'],
  imports: [HttpClientModule, CommonModule]
})
export class ClientComponent implements OnInit {
  clients: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/clients')
      .subscribe(data => this.clients = data);
  }
}
