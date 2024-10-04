package com.nailsSalon.AdriDesign.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

  // Directorio donde se almacenarán los archivos (puede ajustarse según las necesidades)
  private final String uploadDir = "/path/to/uploads/";

  public Course createCourse(Course course) {
    return courseRepository.save(course);
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
    courseRepository.deleteById(id);
  }

  // Subir archivo (imagen de presentación)
  public String uploadFile(MultipartFile file) {
    try {
      // Asegúrate de que el directorio de subida exista
      Path uploadPath = Paths.get(uploadDir);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath); // Crea el directorio si no existe
      }

      // Crea la ruta completa del archivo
      Path filePath = uploadPath.resolve(file.getOriginalFilename());
      Files.write(filePath, file.getBytes()); // Escribe el archivo en el sistema de archivos

      return filePath.toString();  // Retorna la ruta o URL del archivo

    } catch (IOException e) {
      throw new RuntimeException("Error al subir archivo: " + file.getOriginalFilename(), e);
    }
  }

  public boolean verifyPayment(UUID courseId, UUID userId) {
    // Logic to verify if the user has paid for the course
    return true; // Return true if the user has paid
  }

}


