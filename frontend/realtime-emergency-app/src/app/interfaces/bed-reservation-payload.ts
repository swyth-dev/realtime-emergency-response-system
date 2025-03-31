export interface BedReservationPayload {
  medicalSpecializationId: string;
  hospitalId: string;
  reservationFirstName: string | null | undefined;
  reservationLastName: string | null | undefined;
  reservationEmail: string | null | undefined;
  reservationPhoneNumber: string | null | undefined
}
