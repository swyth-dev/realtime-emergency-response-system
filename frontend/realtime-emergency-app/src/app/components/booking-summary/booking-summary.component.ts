import {Component, OnInit} from '@angular/core';
import {MatCardModule} from '@angular/material/card'; // Import Angular Material Card module
import {MatButtonModule} from '@angular/material/button';
import {ActivatedRoute, Router} from '@angular/router';
import {BedReservationResponse} from '../../interfaces/bed-reservation-response';
import {MedicalSpecialization} from '../../interfaces/medical-specialization';
import {EmergencyService} from '../../services/emergency.service';
import {HospitalService} from '../../services/hospital.service'; // (Optional) If buttons are needed

@Component({
  selector: 'app-booking-summary',
  standalone: true, // Declares this as a standalone component
  templateUrl: './booking-summary.component.html',
  styleUrls: ['./booking-summary.component.css'],
  imports: [
    MatCardModule, // To use Angular Material cards
    MatButtonModule // Add if buttons are needed (optional)
  ]
})

export class BookingSummaryComponent implements OnInit {

  // Reservation ID to fetch all the related information
  private reservationId: string;

  // Reservation, Specialization, and Hospital objects populated by fetch methods
  // TODO: Refactor this with interfaces
  reservation: any = null;
  specialization: any = null;
  hospital: any = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private emergencyService: EmergencyService, // Service for specialized fetch operations
    private hospitalService: HospitalService, // Service for hospital-related operations
  ) {
    this.reservationId = '';
  }

  ngOnInit(): void {
    // Retrieve specialization ID and name from query params
    this.route.queryParams.subscribe((params) => {
      this.reservationId = params['reservationId'] || null;

      if (this.reservationId) {
        this.fetchBedReservationById(this.reservationId);
      }
    });
  }

  /**
   * Fetch Bed Reservation by ID and save reservation details.
   * Combines data from EmergencyService and HospitalService.
   */
  fetchBedReservationById(reservationId: string): void {
    this.emergencyService.getReservationById(reservationId).subscribe(
      (data: BedReservationResponse) => {
        // Save the fetched information into the reservation object
        this.reservation = {
          reservationFirstName: data.reservationFirstName,
          reservationLastName: data.reservationLastName,
          reservationEmail: data.reservationEmail,
          reservationPhoneNumber: data.reservationPhoneNumber,
        };

        // Fetch additional details such as hospital and specialization
        if (data.hospitalId != null) {
          this.fetchHospitalById(data.hospitalId.toString());
        }
        if (data.medicalSpecializationId != null) {
          this.fetchMedicalSpecializationById(data.medicalSpecializationId.toString());
        }
      },
      (error) => {
        console.error('Error fetching reservation:', error); // Log error if any
      }
    );
  }

  /**
   * Fetch Hospital by ID and save hospital information.
   */
  fetchHospitalById(hospitalId: string): void {
    this.hospitalService.getHospitalById(hospitalId).subscribe(
      (data) => {
        // Save the fetched hospital details
        this.hospital = {
          name: data.name,
          address: data.address,
          postcode: data.postCode,
          city: data.city
        };
      },
      (error) => {
        console.error('Error fetching hospital:', error); // Log error if any
      }
    );
  }

  /**
   * Fetch Medical Specialization by ID and save specialization information.
   */
  fetchMedicalSpecializationById(specializationId: string): void {
    this.hospitalService.getSpecializationById(specializationId).subscribe(
      (data: MedicalSpecialization) => {
        // Save the fetched specialization details
        this.specialization = {
          name: data.name,
          groupName: data.group,
        };
      },
      (error) => {
        console.error('Error fetching specialization:', error); // Log error if any
      }
    );
  }



  // Mock data for demonstration; replace this with actual data passed from a parent or service
  // reservation = {
  //   reservationFirstName: 'Yoann',
  //   reservationLastName: 'Talon',
  //   reservationEmail: 'yoann@mail.com',
  //   reservationPhoneNumber: '+333333'
  // };
  //
  // specialization = {
  //   name: 'Cardiology',
  //   groupName: 'Heart Specialists'
  // };
  //
  // hospital = {
  //   name: 'Saint Mary Hospital',
  //   address: '123 Main Street',
  //   postcode: '90210',
  //   city: 'Beverly Hills',
  //   country: 'USA'
  // };

  // Navigate back to home
  goToHome(): void {
    this.router.navigate(['/']);
  }

}
