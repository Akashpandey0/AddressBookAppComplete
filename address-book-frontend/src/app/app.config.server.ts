import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { AddContactComponent } from './components/add-contact/add-contact.component';
import { ContactListComponent } from './components/contact-list/contact-list.component';

export const config: ApplicationConfig = {
  providers: [
    provideRouter([
      { path: '', redirectTo: 'contacts', pathMatch: 'full' },
      { path: 'contacts', component: ContactListComponent },
      { path: 'add-contact', component: AddContactComponent }
    ]),
    provideHttpClient(),
    importProvidersFrom(ReactiveFormsModule)
  ]
};
