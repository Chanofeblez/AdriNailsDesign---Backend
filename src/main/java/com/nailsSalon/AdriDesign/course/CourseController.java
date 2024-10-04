package com.nailsSalon.AdriDesign.course;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nailsSalon.AdriDesign.servicio.Servicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = {"http://localhost:8100", "https://adrinailsdesign-c393e5baf34a.herokuapp.com"})
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

  @Autowired
  private CourseService courseService;

  // Crear un curso con imagen de presentación
  @PostMapping
  public Course createCourse(@RequestBody Course course) {
    return courseService.createCourse(course);
  }

  @GetMapping
  public List<Course> getAllCourses() {
    return courseService.getAllCourses();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourse(@PathVariable UUID id) {
    Optional<Course> course = courseService.getCourseById(id);
    return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable UUID id,
                                             @RequestPart("course") String courseJson,
                                             @RequestPart(value = "image", required = false) MultipartFile image) {

    Optional<Course> existingCourse = courseService.getCourseById(id);
    if (existingCourse.isPresent()) {

      // Convierte el JSON a tu objeto Course usando ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();
      Course course = null;

      try {
        course = objectMapper.readValue(courseJson, Course.class);
      } catch (JsonProcessingException e) {
        logger.error("Error al deserializar el JSON del curso", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El JSON del curso es inválido", e);
      }

      // Subir nueva imagen si se proporciona
      if (image != null && !image.isEmpty()) {
        String imageUrl = courseService.uploadFile(image);
        course.setImagePath(imageUrl);
      }

      course.setId(id);  // Asegurar que estamos actualizando el curso correcto
      return ResponseEntity.ok(courseService.updateCourse(course));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
    courseService.deleteCourse(id);
    return ResponseEntity.noContent().build();
  }
}


