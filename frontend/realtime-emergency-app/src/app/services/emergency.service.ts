import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {BedReservationPayload} from '../interfaces/bed-reservation-payload';
import {MedicalSpecialization} from '../interfaces/medical-specialization';
import {BedReservationResponse} from '../interfaces/bed-reservation-response';

@Injectable({
  providedIn: 'root'
})
export class EmergencyService {
  private baseUrl = environment.emergencyServiceBaseUrl;

  constructor(private http: HttpClient) {}

  reserveBed(payload: BedReservationPayload): Observable<any> {
    return this.http.post(`${this.baseUrl}/bed-reservations`, payload);
  }

  getReservationById(bedReservationId: string): Observable<BedReservationResponse> {
    return this.http.get<BedReservationResponse>(`${this.baseUrl}/bed-reservations/${bedReservationId}`);
  }
}
