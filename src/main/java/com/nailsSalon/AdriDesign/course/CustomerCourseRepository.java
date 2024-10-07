package com.nailsSalon.AdriDesign.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerCourseRepository extends JpaRepository<CustomerCourse, Long> {

  // Método para verificar si un usuario ha pagado un curso específico
  Optional<CustomerCourse> findByCustomerIdAndCourseId(Long customerId, UUID courseId);

  // Busca los cursos pagados por un cliente
  @Query("SELECT c.course FROM CustomerCourse c WHERE c.customer.id = :customerId AND c.paymentStatus = true")
  List<Course> findCoursesByCustomerIdAndPaymentStatusTrue(@Param("customerId") Long customerId);
}

