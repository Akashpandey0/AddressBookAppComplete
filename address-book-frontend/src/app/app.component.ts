import { Component, OnInit } from '@angular/core';
import { ContactService } from './services/contact.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  // Add ReactiveFormsModule

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],  // Add ReactiveFormsModule
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  contacts: any[] = [];
  newContact = {
    name: '',
    phone: '',
    email: ''
  };

  constructor(private contactService: ContactService) {}

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts(): void {
    this.contactService.getAllContacts().subscribe({
      next: (data) => this.contacts = data,
      error: (err) => console.error('Error loading contacts:', err)
    });
  }

  addContact(): void {
    this.contactService.createContact(this.newContact).subscribe({
      next: () => {
        alert('Contact added successfully!');
        this.loadContacts();
      },
      error: (err) => console.error('Error adding contact:', err)
    });
  }
}
