package com.nailsSalon.AdriDesign.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

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

  public boolean verifyPayment(UUID courseId, UUID userId) {
    // Logic to verify if the user has paid for the course
    return true; // Return true if the user has paid
  }
}

