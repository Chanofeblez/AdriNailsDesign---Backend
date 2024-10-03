export interface CustomerInterface {
  id: string; // Este campo es opcional ya que se genera en el backend.
  name: string;
  email: string;
  password: string;
  phoneNumber: string;
  enabled?: boolean; // Estos campos también pueden ser opcionales dependiendo de si los manejas en el frontend o solo en el backend.
  accountNotExpired?: boolean;
  accountNotLocked?: boolean;
  credentialNotExpired?: boolean;
  createdAt?: string; // Puede ser Date, pero si recibes un string del backend, úsalo como string.
  updatedAt?: string;
  paymentMethodId?: string; // Si estás almacenando el método de pago.
}
