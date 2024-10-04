package com.nailsSalon.AdriDesign.course;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  // Crear un curso (solo con título, descripción y precio)
  @PostMapping
  public Course createCourse(@RequestBody Course course) {
    logger.info("Creando curso: {}", course);
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

  // Actualizar un curso
  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable UUID id, @RequestBody Course course) {
    Optional<Course> existingCourse = courseService.getCourseById(id);
    if (existingCourse.isPresent()) {
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

