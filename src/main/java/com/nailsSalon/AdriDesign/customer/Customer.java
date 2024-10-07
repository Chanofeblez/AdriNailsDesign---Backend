package com.nailsSalon.AdriDesign.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nailsSalon.AdriDesign.appointment.Appointment;
import com.nailsSalon.AdriDesign.course.CustomerCourse;
import com.nailsSalon.AdriDesign.payment.SalonPayment;
import com.squareup.square.models.Payment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

  // Relación con los cursos del cliente
  @OneToMany(mappedBy = "customer")
  private List<CustomerCourse> customerCourses;

    //private List<Appointment> appointments = new ArrayList<>();

    //@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference // Rompe la recursividad para la relación con SalonPayment
    //private List<SalonPayment> payments = new ArrayList<>();

    private String paymentMethodId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
