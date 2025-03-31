import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalService } from '../../services/hospital.service';
import {NearestHospital} from '../../interfaces/nearest-hospital';
import {BedReservationPayload} from '../../interfaces/bed-reservation-payload';
import {EmergencyService} from '../../services/emergency.service';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {EmergencyLocation} from '../../interfaces/emergency-location';
import {NgIf} from '@angular/common';
import {ErrorHandlerService} from '../../services/error-handler.service';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';

declare var google: any; // Declare Google Maps namespace

@Component({
  selector: 'app-nearest-hospital-details',
  templateUrl: './nearest-hospital-details.component.html',
  styleUrls: ['./nearest-hospital-details.component.css'],
  imports: [
    MatCardContent,
    MatCardTitle,
    MatCard,
    MatLabel,
    MatFormField,
    MatButton,
    FormsModule,
    MatProgressSpinner,
    MatCardActions,
    NgIf,
    MatInput,
    MatCardHeader,
    MatCardSubtitle,
    MatSnackBarModule
  ],
  standalone: true
})
export class NearestHospitalDetailsComponent implements OnInit {
  // Only to store the requested medical specialization name
  specializationName: string | null = null;

  // Init emergency location which will be use to request the nearest hospital
  emergencyLocation: EmergencyLocation = {
      medicalSpecializationId: null,
      latitude: null,
      longitude: null
    };

  nearestHospital: NearestHospital | null = null; // Store hospital details

  userAddress: string = ''; // Store selected user address

  isLoading: boolean = false; // For loading spinner

  isReservationSuccessful: boolean = false; // Track success state
  reservationRecap: NearestHospital | null = null; // Store hospital details for recap

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hospitalService: HospitalService,
    private emergencyService: EmergencyService,
    private errorHandler: ErrorHandlerService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    // Retrieve specialization ID and name from query params
    this.route.queryParams.subscribe((params) => {
      this.emergencyLocation.medicalSpecializationId = params['id'] || null
      this.specializationName = params['name'] || null;

      if (!this.emergencyLocation.medicalSpecializationId) {
        console.error('No specialization ID provided!');
        this.router.navigate(['/']);
      }
    });

    // Load Google Maps script dynamically and initialize autocomplete
    this.loadGoogleMapsScript().then(() => {
      this.initializeAutocomplete();
    });
  }

  // Load the Google Maps script dynamically
  loadGoogleMapsScript(): Promise<void> {
    return new Promise((resolve, reject) => {
      if (document.getElementById('googleMapsScript')) {
        resolve(); // Already loaded
        return;
      }

      const script = document.createElement('script');
      script.id = 'googleMapsScript';
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyBsMJeWwoV32ef2NcMsZAz00-5ZvTQFMu4&libraries=places`;
      script.onload = () => resolve();
      script.onerror = () => reject('Failed to load Google Maps script.');
      document.body.appendChild(script);
    });
  }

  // Initialize Google Places Autocomplete
  initializeAutocomplete(): void {
    const autocompleteInput = document.getElementById(
      'autocomplete'
    ) as HTMLInputElement;

    const autocomplete = new google.maps.places.Autocomplete(autocompleteInput);
    autocomplete.setFields(['geometry', 'formatted_address']);
    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();

      if (place.geometry) {
        this.userAddress = place.formatted_address;
        this.emergencyLocation.latitude = place.geometry.location.lat();
        this.emergencyLocation.longitude = place.geometry.location.lng();
        console.log('Address:', this.userAddress);
        console.log('Coordinates:', this.emergencyLocation.latitude, this.emergencyLocation.longitude);
      } else {
        console.error('No geometry found for the address!');
      }
    });
  }

  // Search for the nearest hospital
  onFindNearestHospital(): void {
    if (!this.emergencyLocation.latitude || !this.emergencyLocation.longitude || !this.emergencyLocation.medicalSpecializationId) {
      this.snackBar.open("Please fill in a valid address with a specialization!", 'Close', {
        duration: 5000,
        panelClass: 'error-snackbar',
      });
      return;
    }

    this.isLoading = true; // Show loading spinner

    this.hospitalService
      .getNearestHospital(this.emergencyLocation)
      .subscribe({
        next: (hospital: NearestHospital) => {
          // Handle the success case
          this.nearestHospital = hospital;
          console.log('Nearest Hospital:', this.nearestHospital);
        },
        error: (err) => {
          this.isLoading = false;

          // Use ErrorHandlerService to process the error
          const errorMessage = this.errorHandler.processHttpError(err);

          // Log the error if needed and notify the user
          console.error('Error fetching nearest hospital:', err);

          this.snackBar.open(errorMessage, 'Close', {
            duration: 5000,
            panelClass: 'error-snackbar',
          });
        },
        complete: () => {
          // Final cleanup
          this.isLoading = false; // Hide loading spinner
        },
      });
  }

  // Navigate back to the home page
  goToHome(): void {
    this.router.navigate(['/']);
  }

  onContinueReservation() {
    if (!this.nearestHospital) {
      console.error('No hospital data available for reservation.');
      return;
    }

    this.router.navigate(['/information'], {
      queryParams: {
        specializationId: this.emergencyLocation.medicalSpecializationId,
        hospitalId: this.nearestHospital?.id,
      },
    });
  }
}
