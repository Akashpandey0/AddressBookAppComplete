import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { isPlatformServer } from '@angular/common';

interface Contact {
  name: string;
  phone: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private apiUrl = 'http://localhost:8080/addressbook';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  getAllContacts(): Observable<Contact[]> {
    if (isPlatformServer(this.platformId)) {
      console.warn('Server-side environment detected. Using `fetch` for requests.');
      return new Observable<Contact[]>((observer) => {
        fetch(`${this.apiUrl}/showcontacts`)
          .then((response) => response.json())
          .then((data) => {
            observer.next(data);
            observer.complete();
          })
          .catch((error) => observer.error(error));
      });
    }

    return this.http.get<Contact[]>(`${this.apiUrl}/showcontacts`).pipe(
      catchError(this.handleError)
    );
  }

  createContact(contact: Contact): Observable<Contact> {
    return this.http.post<Contact>(`${this.apiUrl}/create`, contact).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
