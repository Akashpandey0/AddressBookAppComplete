import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import 'zone.js'; // ✅ Correct import for Angular SSR
import 'cross-fetch/polyfill';

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
