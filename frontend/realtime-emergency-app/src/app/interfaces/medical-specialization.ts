import {Hospital} from './hospital';

export interface MedicalSpecialization {
  id: number;
  name: string;
  group: string;
  hospitals: Hospital[];
}
