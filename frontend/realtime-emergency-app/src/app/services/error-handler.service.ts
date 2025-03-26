import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root', // Makes it globally available
})
export class ErrorHandlerService {
  constructor() {}

  /**
   * Handle and format HTTP errors.
   * Convert backend errors into user-friendly messages.
   */
  processHttpError(error: HttpErrorResponse): string {
    if (!navigator.onLine) {
      return 'No Internet connection. Please check your network and try again.';
    }

    let errorMessage = 'An unknown error occurred.';
    if (error.error instanceof Object) {
      errorMessage = error.error.message || errorMessage;
    }

    // Optional: Add more error scenarios for specific HTTP statuses
    switch (error.status) {
      case 401:
        errorMessage = 'Unauthorized! Please log in again.';
        break;
      case 404:
        errorMessage;
        break;
      case 500:
        errorMessage = 'A server error occurred. Please try again later.';
        break;
    }

    return errorMessage;
  }

  /**
   * Example: Log errors to an external monitoring tool (optional).
   */
  logError(error: any): void {
    console.error('Error:', error);
    // Code to send error to external service like Sentry or Firebase Crashlytics.
  }
}
