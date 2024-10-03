export interface Image {
  id: string;                // UUID
  title: string;             // Título de la imagen
  data: Uint8Array;          // Los datos de la imagen en formato byte[]
  contentType: string;       // Tipo de contenido (e.g., image/jpeg)
  imageUrl?: string;         // URL de la imagen generada a partir de los datos
  type: 'Manicure' | 'Pedicure'; // Tipo de imagen, puede ser "Manicure" o "Pedicure"
}
