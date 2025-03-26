import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {HospitalService} from '../../services/hospital.service';
import {MedicalSpecialization} from '../../interfaces/medical-specialization';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';

@Component({
  selector: 'app-specialization-selector',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatCardActions,
    MatCardContent,
    MatCardTitle,
    MatCard,
    MatCardHeader,
    FormsModule
  ],
  templateUrl: './medical-specialization-selector.component.html',
  styleUrls: ['./medical-specialization-selector.component.css'],
  standalone: true
})
export class MedicalSpecializationSelectorComponent implements OnInit {
  specializationControl = new FormControl<MedicalSpecialization | null>(null);
  specializations: MedicalSpecialization[] = [];

  private hospitalService = inject(HospitalService);
  private router = inject(Router);

  ngOnInit(): void {
    this.hospitalService.getSpecializations().subscribe((data) => {
      this.specializations = data;
    });
  }

  onSelectSpecialization(): void {
    const selectedSpecialization = this.specializationControl.value;
    console.log(selectedSpecialization);
    if (selectedSpecialization) {
      // Navigate to hospitals page
      this.router.navigate(['/hospitals'], {
        queryParams: {
          id: selectedSpecialization.id,
          name: selectedSpecialization.name,
        },
      });
    }
  }
}
