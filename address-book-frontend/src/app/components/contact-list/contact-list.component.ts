import { Component, OnInit } from '@angular/core';
import { ContactService } from '../../services/contact.service';
import { CommonModule } from '@angular/common'; // Add this import

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [CommonModule], // Add CommonModule here
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent implements OnInit {
  contacts: any[] = [];

  constructor(private contactService: ContactService) {}

  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.contactService.getAllContacts().subscribe({
      next: (data) => this.contacts = data,
      error: () => alert('Failed to fetch contacts')
    });
  }
}
