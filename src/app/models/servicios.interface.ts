export interface Servicio {
  id: string; // UUID
  name: string;
  description: string;
  price: number; // BigDecimal en Java, number en TypeScript
  type: 'MANICURE' | 'PEDICURE'; // Enum en Java, string literal en TypeScript
  imagePath: string;
  note: string;
  createdAt: string; // LocalDateTime en Java, string en ISO 8601 en TypeScript
  updatedAt: string; // LocalDateTime en Java, string en ISO 8601 en TypeScript
  variants: ServicioVariant[]; // Relación ManyToMany
}

export interface ServicioVariant {
  id: string; // UUID
  name: string;
  description: string;
  price: number; // BigDecimal en Java, number en TypeScript
  createdAt: string; // LocalDateTime en Java, string en ISO 8601 en TypeScript
  updatedAt: string; // LocalDateTime en Java, string en ISO 8601 en TypeScript
}
