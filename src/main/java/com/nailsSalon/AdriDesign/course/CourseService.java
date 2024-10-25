package com.nailsSalon.AdriDesign.course;

import com.nailsSalon.AdriDesign.servicio.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CustomerCourseRepository customerCourseRepository;

  // Directorio donde se almacenarán los archivos (puede ajustarse según las necesidades)
  private final String uploadDir = "/path/to/uploads/";

  public Course createCourse(Course course) {
      // Validación de negocio antes de guardar
      validateCourse(course);
    return courseRepository.save(course);

  }

  // Método para verificar si un usuario ha pagado un curso
  public boolean verifyPayment(UUID courseId, UUID userId) {
    Optional<CustomerCourse> customerCourse = customerCourseRepository.findByCustomerIdAndCourseId(userId, courseId);
    return customerCourse.isPresent() && customerCourse.get().isPaymentStatus();
  }

  // Método para obtener los cursos pagados por el usuario
  public List<Course> findPaidCoursesByUserId(UUID userId) {
    return customerCourseRepository.findCoursesByCustomerIdAndPaymentStatusTrue(userId);
  }

  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  public Optional<Course> getCourseById(UUID id) {
    return courseRepository.findById(id);
  }

  public Course updateCourse(Course course) {
    return courseRepository.save(course);
  }

  public void deleteCourse(UUID id) {
    Optional<Course> course = courseRepository.findById(id);

    if (course.isPresent()) {
      Course existingCourse = course.get();

      // Lógica para eliminar archivos relacionados
      if (existingCourse.getImagePath() != null) {
        // Aquí llamas a tu método para eliminar la imagen del almacenamiento
        deleteFile(existingCourse.getImagePath());
      }

      if (existingCourse.getVideoPaths() != null) {
        // Elimina todos los videos asociados
        existingCourse.getVideoPaths().forEach(videoPath -> deleteFile(videoPath));
      }

      if (existingCourse.getPdfPaths() != null) {
        // Elimina todos los PDFs asociados
        existingCourse.getPdfPaths().forEach(pdfPath -> deleteFile(pdfPath));
      }

      // Finalmente, elimina el curso de la base de datos
      courseRepository.deleteById(id);
    } else {
      throw new RuntimeException("Course not found with ID: " + id);
    }
  }

  public String uploadFile(MultipartFile file) {
    try {
      // Asegúrate de que el directorio de subida existe
      Path uploadPath = Paths.get("/path/to/uploads/");
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      // Crea la ruta completa del archivo
      Path filePath = uploadPath.resolve(file.getOriginalFilename());
      Files.write(filePath, file.getBytes());

      return filePath.toString();  // Retorna la ruta del archivo subido
    } catch (IOException e) {
      throw new RuntimeException("Error uploading file: " + file.getOriginalFilename(), e);
    }
  }


  public void deleteFile(String filePath) {
    Path path = Paths.get(filePath);
    try {
      Files.deleteIfExists(path); // Elimina el archivo si existe
      System.out.println("Archivo eliminado: " + filePath);
    } catch (IOException e) {
      System.err.println("Error al eliminar el archivo: " + filePath);
      throw new RuntimeException("Error al eliminar el archivo: " + filePath, e);
    }
  }



  private void validateCourse(Course course) {
    // Ejemplo de validación: Verificar que el precio no sea negativo
    if (course.getPrice().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("El precio no puede ser negativo");
    }
    // Puedes agregar más validaciones según las necesidades del negocio
  }

}
