import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalService } from '../../services/hospital.service';
import {NearestHospital} from '../../interfaces/nearest-hospital';
import {BedReservationPayload} from '../../interfaces/bed-reservation-payload';
import {EmergencyService} from '../../services/emergency.service';
import {MatCard, MatCardActions, MatCardContent, MatCardTitle} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {EmergencyLocation} from '../../interfaces/emergency-location';
import {NgIf} from '@angular/common';

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
    MatInput
  ],
  standalone: true
})
export class NearestHospitalDetailsComponent implements OnInit {
  specializationId: number = 0;
  specializationName: string | null = null;
  latitude: number | null = null; // Latitude from selected location
  longitude: number | null = null; // Longitude from selected location
  userAddress: string = ''; // Store selected user address
  nearestHospital: NearestHospital | null = null; // Store hospital details
  isLoading: boolean = false; // For loading spinner

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hospitalService: HospitalService,
    private emergencyService: EmergencyService
  ) {}

  ngOnInit(): void {
    // Retrieve specialization ID and name from query params
    this.route.queryParams.subscribe((params) => {
      this.specializationId = params['id'] || null;
      this.specializationName = params['name'] || null;

      if (!this.specializationId) {
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
        this.latitude = place.geometry.location.lat();
        this.longitude = place.geometry.location.lng();
        console.log('Address:', this.userAddress);
        console.log('Coordinates:', this.latitude, this.longitude);
      } else {
        console.error('No geometry found for the address!');
      }
    });
  }

  // Search for the nearest hospital
  onFindNearestHospital(): void {
    if (!this.latitude || !this.longitude || !this.specializationId) {
      alert('Please fill in a valid address with a specialization!');
      return;
    }

    this.isLoading = true; // Show loading spinner

    const emergencyLocation: EmergencyLocation = {
      medicalSpecializationId: this.specializationId,
      latitude: this.latitude,
      longitude: this.longitude
    };

    this.hospitalService
      .getNearestHospital(emergencyLocation)
      .subscribe({
        next: (hospital: NearestHospital) => {
          // Handle the success case
          this.nearestHospital = hospital;
          console.log('Nearest Hospital:', this.nearestHospital);
        },
        error: (error) => {
          // Handle the error case
          console.error('Error fetching nearest hospital:', error);
        },
        complete: () => {
          // Final cleanup
          this.isLoading = false; // Hide loading spinner
        },
      });
  }

  // Reserve a bed at the nearest hospital
  onReserveBed(): void {
    if (!this.nearestHospital) {
      console.error('No hospital data available for reservation.');
      return;
    }

    const reservationPayload: BedReservationPayload = {
      medicalSpecializationId: this.specializationId,
      hospitalId: this.nearestHospital.id

    };

    this.emergencyService
      .reserveBed(reservationPayload)
      .subscribe({
        next: () => {
          alert(
            `Successfully reserved a bed at ${this.nearestHospital?.name}!`
          );
        },
        error: (error) => {
          console.error('Error reserving a bed:', error);
        },
      });
  }
}
