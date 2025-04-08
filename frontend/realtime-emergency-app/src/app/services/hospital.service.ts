import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {MedicalSpecialization} from '../interfaces/medical-specialization';
import {NearestHospital} from '../interfaces/nearest-hospital';
import {EmergencyLocation} from '../interfaces/emergency-location';
import {Hospital} from '../interfaces/hospital';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getSpecializations(): Observable<MedicalSpecialization[]> {
    return this.http.get<MedicalSpecialization[]>(`${this.baseUrl}/specializations`);
  }

  getSpecializationById(medicalSpecializationId: string): Observable<MedicalSpecialization> {
    return this.http.get<MedicalSpecialization>(`${this.baseUrl}/specializations/${medicalSpecializationId}`);
  }

  getHospitalById(hospitalId: string): Observable<Hospital> {
    return this.http.get<Hospital>(`${this.baseUrl}/hospitals/${hospitalId}`);
  }

  getNearestHospital(payload: EmergencyLocation): Observable<NearestHospital> {
    return this.http.post<NearestHospital>(`${this.baseUrl}/hospitals/nearest`, payload);
  }
}
