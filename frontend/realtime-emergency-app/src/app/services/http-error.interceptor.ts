import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './error-handler.service';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export const HttpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  // Inject dependencies directly
  const snackBar = inject(MatSnackBar);
  const errorHandler = inject(ErrorHandlerService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // Use the ErrorHandlerService to process the error
      const errorMessage = errorHandler.processHttpError(error);

      // Example: Notify the user using MatSnackBar
      snackBar.open(errorMessage, 'Close', {
        duration: 5000,
        panelClass: 'error-snackbar',
      });

      // Return an observable with a user-facing error message
      return throwError(() => new Error(errorMessage));
    })
  );
};
