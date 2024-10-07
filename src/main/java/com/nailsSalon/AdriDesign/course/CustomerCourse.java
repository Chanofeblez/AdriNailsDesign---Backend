package com.nailsSalon.AdriDesign.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nailsSalon.AdriDesign.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(indexes = {@Index(name = "idx_customer_course", columnList = "customer_id, course_id")})
public class CustomerCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  private boolean paymentStatus;  // Si el curso ha sido pagado o no

  // Constructor, getters, setters
}

