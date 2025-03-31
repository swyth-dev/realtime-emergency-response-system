export interface Hospital {
  id: number;
  name: string;
  address: string;
  postCode: string;
  city: string;
  latitude: number;
  longitude: number;
  specializations: MedicalSpecializationAvailability[];
}

interface MedicalSpecializationAvailability {
  id: number ;
  name: string;
  bedsAvailable: number;
}
