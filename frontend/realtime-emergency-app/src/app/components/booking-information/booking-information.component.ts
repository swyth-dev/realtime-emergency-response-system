import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators, ɵValue} from '@angular/forms';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {NgIf} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {ActivatedRoute, Router} from '@angular/router';
import {BedReservationPayload} from '../../interfaces/bed-reservation-payload';
import {EmergencyService} from '../../services/emergency.service';
import {HospitalService} from '../../services/hospital.service';
import {ErrorHandlerService} from '../../services/error-handler.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {BedReservationResponse} from '../../interfaces/bed-reservation-response';

@Component({
  selector: 'app-booking-information',
  imports: [
    FormsModule,
    MatButton,
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle,
    MatFormField,
    MatInput,
    MatLabel,
    MatProgressSpinner,
    NgIf,
    ReactiveFormsModule,
    MatError
  ],
  templateUrl: './booking-information.component.html',
  styleUrl: './booking-information.component.css',
  standalone: true,
})

export class BookingInformationComponent implements OnInit {
  // Ids used to fetch data and display related names on the view, instead of storing data client-side
  medicalSpecializationId!: string;
  hospitalId!: string;
  specializationName!: string | null;
  hospitalName!: string | null;

  // Reactive booking form with some specific validators to ensure data consistency
  bookingForm = new FormGroup({
    reservationFirstName: new FormControl('', Validators.required),
    reservationLastName: new FormControl('', Validators.required),
    reservationEmail: new FormControl('', [Validators.required, Validators.email]),
    reservationPhoneNumber: new FormControl('', [Validators.required, Validators.pattern("\\+?[1-9]\\d{1,14}")]), // E.164 Format regexp pattern
  });

  // Used for spinned
  isLoading: boolean= false;
  // Var to route to the next view
  protected isReservationSuccessful: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hospitalService: HospitalService,
    private emergencyService: EmergencyService,
    private errorHandler: ErrorHandlerService,
    private snackBar: MatSnackBar
  ) {
    this.isReservationSuccessful = false;
  }

  ngOnInit(): void {
    // Fetch query params
    this.route.queryParams.subscribe((params) => {
      this.medicalSpecializationId = params['specializationId'];
      this.hospitalId = params['hospitalId'];

      // Call functions to fetch data based on these IDs
      this.fetchSpecializationName(this.medicalSpecializationId);
      this.fetchHospitalName(this.hospitalId);
    });
  }

  // Call web API to display name
  fetchSpecializationName(id: string): void {
    this.hospitalService.getSpecializationById(id).subscribe({
      next: (response) => {
        // Assuming the response contains specialization name as `name`
        this.specializationName = response.name;
      },
      error: (error) => {
        console.error('Error fetching specialization:', error); // Handle error appropriately
        this.specializationName = 'Error fetching specialization'; // Optionally set a fallback value
      }
    });
  }

  // Call web API to display name
  fetchHospitalName(id: string): void {
    this.hospitalService.getHospitalById(id).subscribe({
      next: (response) => {
        // Assuming the response contains hospital name as `name`
        this.hospitalName = response.name;
      },
      error: (error) => {
        console.error('Error fetching hospital name:', error); // Handle error appropriately
        this.hospitalName = 'Error fetching hospital name'; // Optionally set a fallback value
      }
    });
  }

  // Handle form submission
  onReserveBed(): void {
    if (this.bookingForm.valid) {
      const reservationPayload: BedReservationPayload = {
        medicalSpecializationId: this.medicalSpecializationId,
        hospitalId: this.hospitalId,
        reservationFirstName: this.bookingForm.get('reservationFirstName')?.value,
        reservationLastName: this.bookingForm.get('reservationLastName')?.value,
        reservationEmail: this.bookingForm.get('reservationEmail')?.value,
        reservationPhoneNumber: this.bookingForm.get('reservationPhoneNumber')?.value
      };

      console.log('Reservation Payload:', reservationPayload);

      this.isLoading = true;

      this.emergencyService.reserveBed(reservationPayload).subscribe({
        next: (response: BedReservationResponse) => {
          this.isLoading = false;

          // Assuming the backend response contains a property reservationId
          const reservationId: string | null = response.id ? response.id.toString() : null;

          if (reservationId) {
            // Navigate to the 'summary' page and pass the reservationId as a query parameter
            this.router.navigate(['/summary'], { queryParams: { reservationId: reservationId } });
          } else {
            console.error('Missing reservationId in the response.');
            this.snackBar.open('Failed to retrieve reservation details. Please try again.', 'Close', {
              duration: 3000,
            });
          }
        },
        error: (error) => {
          this.isLoading = false;

          // Use ErrorHandlerService to process the error
          const errorMessage = this.errorHandler.processHttpError(error);

          // Log the error if needed and notify the user
          console.error('Error reserving a bed:', errorMessage);

          this.snackBar.open(errorMessage, 'Close', {
            duration: 5000,
            panelClass: 'error-snackbar',
          });
        },
      });
    }
  }

  // Navigate back to home
  goToHome(): void {
    this.router.navigate(['/']);
  }
}
