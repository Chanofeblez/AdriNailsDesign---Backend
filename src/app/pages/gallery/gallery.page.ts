import { Component, inject, OnInit } from '@angular/core';
import { AlertController, ToastController } from '@ionic/angular';
import { Image } from 'src/app/models/image.interface';
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.page.html',
  styleUrls: ['./gallery.page.scss'],
})
export class GalleryPage implements OnInit {

  title: string = '';
  selectedFile: File | null = null;
  images: Image[] = [];
  currentSegment: string = 'images';
  imageType: string = 'Manicure';  // Valor inicial

  private imageService    = inject(ImageService);
  private toastController = inject(ToastController);

  constructor() {}

  ngOnInit() {
    this.loadImages();
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadImage() {
    if (this.selectedFile && this.title && this.imageType) {
      this.imageService.uploadImage(this.title, this.imageType, this.selectedFile).subscribe({
        next: (res) => {
          console.log('Imagen subida correctamente');
          this.loadImages(); // Recargar la lista de imágenes
        },
        error: (err) => console.error('Error al subir la imagen', err),
      });
    }
  }

  loadImages() {
    this.imageService.getAllImages().subscribe({
      next: (res: Image[]) => {
        this.images = res;
        console.log("this.images después de cargar", this.images); // Mover el console.log aquí
      },
      error: (err) => console.error('Error al cargar imágenes', err),
    });
  }

  deleteImage(id: string) {
    this.toastController.create({
      message: 'Are you sure you want to delete this image?',
      duration: 5000,
      position: 'bottom',
      color: 'light', // Color del toast para la confirmación
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel'
        },
        {
          text: 'Delete',
          handler: () => {
            this.imageService.deleteImage(id).subscribe({
              next: () => {
                this.showToast('Image successfully deleted', 'success');
                this.loadImages(); // Recargar lista de imágenes
              },
              error: (err) => {
                this.showToast('Failed to delete image', 'danger');
                console.error('Error deleting image', err);
              }
            });
          }
        }
      ]
    }).then(toast => toast.present());
  }

  showToast(message: string, color: string) {
    this.toastController.create({
      message: message,
      duration: 2000,
      position: 'bottom',
      color: color  // Aquí puedes especificar el color ('success' o 'danger')
    }).then(toast => toast.present());
  }




  imageUrl(id: string): string {
    return this.imageService.getImageUrl(id);
  }

}



