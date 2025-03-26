import { Routes } from '@angular/router';
import {
  MedicalSpecializationSelectorComponent
} from './components/medical-specialization-selector/medical-specialization-selector.component';
import {
  NearestHospitalDetailsComponent
} from './components/nearest-hospital-details/nearest-hospital-details.component';

export const routes: Routes = [
  { path: '', component: MedicalSpecializationSelectorComponent },   // Main form screen
  { path: 'hospitals', component: NearestHospitalDetailsComponent }  // Nearest Hospital display screen
];
