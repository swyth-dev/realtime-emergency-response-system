import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {HttpErrorInterceptor} from './services/http-error.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideAnimationsAsync(),
    provideZoneChangeDetection({ eventCoalescing: true }), // Optimize Angular's change detection
    provideRouter(routes), // Application routes
    provideHttpClient(withInterceptors([HttpErrorInterceptor])) // HTTP client for API communication
  ]
};
