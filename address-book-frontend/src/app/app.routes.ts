import { Routes } from '@angular/router';
import { AddContactComponent } from './components/add-contact/add-contact.component';
import { ContactListComponent } from './components/contact-list/contact-list.component';

export const routes: Routes = [
  { path: 'add-contact', component: AddContactComponent },
  { path: 'contact-list', component: ContactListComponent },
  { path: '', redirectTo: 'contact-list', pathMatch: 'full' }
];
