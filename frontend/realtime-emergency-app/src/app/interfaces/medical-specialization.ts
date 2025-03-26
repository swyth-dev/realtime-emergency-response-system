import {Hospital} from './hospital';

export interface MedicalSpecialization {
  id: number | null;
  name: string;
  group: string;
  hospitals: Hospital[];
}
