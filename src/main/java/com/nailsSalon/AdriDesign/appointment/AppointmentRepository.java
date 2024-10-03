package com.nailsSalon.AdriDesign.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Encuentra todas las citas para un cliente específico
    List<Appointment> findByCustomerEmail(String customerEmail);

    // Encuentra todas las citas para un servicio específico
    List<Appointment> findByServiceName(String serviceName);

    // Encuentra todas las citas en una fecha específica
    List<Appointment> findByAppointmentDate(LocalDateTime appointmentDate);

    // Encuentra todas las citas confirmadas
    List<Appointment> findByStatus(AppointmentStatus status);

    //Optional<Appointment> findByIdAndUserId(UUID appointmentId, UUID userId);



}
