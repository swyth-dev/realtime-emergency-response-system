export interface MedicalSpecialization {
  id: number | null;
  name: string;
  group: string;
  hospitals: HospitalAvailability[];
}

interface HospitalAvailability {
  id: number;
  name: string;
  address: string;
  postCode: string;
  city: string;
  latitude: number;
  longitude: number;
  bedsAvailable: number;
}
