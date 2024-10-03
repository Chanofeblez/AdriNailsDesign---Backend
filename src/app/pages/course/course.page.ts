import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.page.html',
  styleUrls: ['./course.page.scss'],
})
export class CoursePage implements OnInit {

  courseForm: FormGroup;
  selectedImage: File | null = null;
  selectedPdf: File | null = null;
  selectedVideos: File[] = [];
  maxFileSizeMB = 30; // Tamaño máximo permitido para los archivos en MB
  errorMessage = '';

  private fb = inject(FormBuilder);
  private courseService = inject(CourseService);

  constructor() {}

  ngOnInit(): void {
    this.courseForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      image: [null],
      pdf: [null],
      videos: [null]
    });
  }

  onFileChange(event: any, type: string): void {
    console.log("Starting OnfileChange");
    const file = event.target.files[0];
    const fileSizeMB = file.size / (1024 * 1024); // Convertimos el tamaño del archivo a MB
    console.log("fileSizeMB", fileSizeMB );

    if (fileSizeMB > this.maxFileSizeMB) {
      this.errorMessage = `El archivo ${file.name} supera el límite de ${this.maxFileSizeMB} MB.`;
      return;
    }

    this.errorMessage = ''; // Limpiamos el mensaje de error si el archivo es válido

    if (type === 'image') {
      this.selectedImage = file;
    } else if (type === 'pdf') {
      this.selectedPdf = file;
    } else if (type === 'videos') {
      this.selectedVideos = Array.from(event.target.files);
    }
  }

  onSubmit(): void {
    let totalSize = 0;

    // Calcula el tamaño del curso en sí (sin archivos)
    const courseData = JSON.stringify(this.courseForm.value);
    totalSize += new Blob([courseData]).size; // Tamaño del curso como JSON

    // Calcula el tamaño de la imagen
    if (this.selectedImage) {
      totalSize += this.selectedImage.size;
    }

    // Calcula el tamaño del PDF
    if (this.selectedPdf) {
      totalSize += this.selectedPdf.size;
    }

    // Calcula el tamaño de los videos
    this.selectedVideos.forEach((video) => {
      totalSize += video.size;
    });

    console.log(`Total FormData size: ${(totalSize / (1024 * 1024)).toFixed(2)} MB`);

    // Verificación del tamaño antes de proceder
    const maxSizeMB = 30; // Límite que hemos definido
    if (totalSize > maxSizeMB * 1024 * 1024) {
      this.errorMessage = `El tamaño total de los archivos supera el límite de ${maxSizeMB} MB.`;
      return;
    }

    const formData = new FormData();
    formData.append('course', JSON.stringify(this.courseForm.value));

    if (this.selectedImage) {
      formData.append('image', this.selectedImage);
    }

    if (this.selectedPdf) {
      formData.append('pdf', this.selectedPdf);
    }

    this.selectedVideos.forEach((video) => {
      formData.append('videos', video);
    });

    // Envía el formulario si todo está bien
    this.courseService.createCourse(formData).subscribe({
      next: (response) => {
        console.log('Course created successfully', response);
      },
      error: (err) => {
        this.errorMessage = 'Hubo un error al crear el curso. Inténtalo nuevamente.';
        console.error('Error creating course', err);
      }
    });
}

}
