import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'; // Add ReactiveFormsModule
import { ContactService } from '../../services/contact.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Add ReactiveFormsModule
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent {
  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private contactService: ContactService) {
    this.contactForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  submitForm() {
    if (this.contactForm.valid) {
      this.contactService.createContact(this.contactForm.value).subscribe({
        next: () => alert('Contact added successfully'),
        error: () => alert('Failed to add contact')
      });
    }
  }
}
