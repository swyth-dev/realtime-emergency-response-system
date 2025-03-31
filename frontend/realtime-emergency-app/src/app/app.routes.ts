import {Routes} from '@angular/router';
import {
  MedicalSpecializationSelectorComponent
} from './components/medical-specialization-selector/medical-specialization-selector.component';
import {
  NearestHospitalDetailsComponent
} from './components/nearest-hospital-details/nearest-hospital-details.component';
import {BookingInformationComponent} from './components/booking-information/booking-information.component';
import {BookingSummaryComponent} from './components/booking-summary/booking-summary.component';

export const routes: Routes = [
  {path: '', component: MedicalSpecializationSelectorComponent},   // Main form screen
  {path: 'hospitals', component: NearestHospitalDetailsComponent},  // Nearest Hospital display screen
  {path: 'information', component: BookingInformationComponent},  // Booking personal information form
  {path: 'summary', component: BookingSummaryComponent}  // Nearest Hospital display screen
];
