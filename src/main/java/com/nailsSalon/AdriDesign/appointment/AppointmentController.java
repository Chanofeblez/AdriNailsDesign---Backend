package com.nailsSalon.AdriDesign.appointment;

import com.nailsSalon.AdriDesign.dto.AppointmentRequestDTO;
import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import com.nailsSalon.AdriDesign.payment.PaymentController;
import com.nailsSalon.AdriDesign.review.Review;
import com.nailsSalon.AdriDesign.review.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:8100")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;

    private final ReviewRepository reviewRepository;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ReviewRepository reviewRepository) {
        this.appointmentService = appointmentService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable UUID id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUserEmail(@PathVariable String userEmail) {
        logger.info("user: {}", userEmail);
        List<Appointment> appointments = appointmentService.getAppointmentsByUserEmail(userEmail);
        logger.info("appointment", appointments);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDTO appointmentDTO) {
        logger.info("Appointment Request DTO: {}", appointmentDTO);
        try {
            // Asume que el DTO contiene los IDs necesarios
            String customerEmail = appointmentDTO.getCustomerEmail();
            logger.info("Captura de email1: {}", appointmentDTO.getCustomerEmail());
            logger.info("Captura de email2: {}", customerEmail);
            String serviceName = appointmentDTO.getServiceName();
            List<UUID> serviceVariantIds = appointmentDTO.getServiceVariantIds();

            logger.info("Antes del servicio");

            // Llama al servicio para crear el appointment pasando los IDs
            Appointment createdAppointment = appointmentService.createAppointment(customerEmail, serviceName, serviceVariantIds,
                    appointmentDTO.getAppointmentDate(), appointmentDTO.getAppointmentTime(),
                    appointmentDTO.getTotalCost(), appointmentDTO.getStatus(), appointmentDTO.getImagePath());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating appointment");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable UUID id, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");

        if (status == null) {
            return ResponseEntity.badRequest().body(null);
        }

        AppointmentStatus appointmentStatus;

        try {
            System.out.println("try: " + status);
            appointmentStatus = AppointmentStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            System.out.println("Catch: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(id, appointmentStatus);
        return ResponseEntity.ok(updatedAppointment);
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable UUID appointmentId,
            @RequestBody Map<String, String> request) {

        String statusString = request.get("status");
        AppointmentStatus status = AppointmentStatus.valueOf(statusString.toUpperCase());
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<?> addReview(
            @PathVariable UUID id,
            @RequestParam("reviewText") String reviewText,
            @RequestParam("rating") Integer rating,
            @RequestParam("photo") MultipartFile photo) {

        System.out.println("Dentro del review");
        Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(id);
        logger.info("optionalAppointment: {}", optionalAppointment);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();

            System.out.println("Creando el review");
            Review review = new Review();
            review.setReviewText(reviewText);
            review.setRating(rating);


            System.out.println("Antes del try");
            try {
                // Manejo del archivo de imagen
                if (photo != null && !photo.isEmpty()) {
                    System.out.println("Dentro del try y el if");

                    // Convertir archivo a byte[] y almacenar en el objeto Review
                    byte[] fileBytes = photo.getBytes();

                    // Verificación de datos y guardado temporal solo para depuración
                    System.out.println("Tamaño del archivo en bytes: " + fileBytes.length);
                    System.out.println("Primeros 100 bytes: " + Arrays.toString(Arrays.copyOf(fileBytes, 100)));

                    // Guardar temporalmente en el sistema de archivos
                    Path path = Paths.get("C:\\Users\\jfern\\Documents" + photo.getOriginalFilename());
                    Files.write(path, fileBytes);
                    System.out.println("Archivo guardado temporalmente en: " + path.toString());

                    // Almacenar la foto como byte[]
                    review.setCustomerPhoto(fileBytes);
                } else {
                    System.out.println("ERROR - BAD_REQUEST).body(Photo is missing or invalid.)");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo is missing or invalid.");
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo.");
            }
            // Asocia la review con el appointment
            System.out.println("Asociando review con appointment");
            review.setAppointment(appointment);
            appointment.setReview(review);

            System.out.println("Guardando ambos (appointment y review)");
            // Ahora guarda tanto el appointment como el review juntos
            appointmentService.saveAppointment(appointment);  // Esto debería guardar tanto la review como el appointment

            System.out.println("Guardado exitoso");
            return ResponseEntity.ok(Map.of("message", "Review and photo added successfully."));
        }
        return ResponseEntity.badRequest().body("Appointment not found.");
    }
}

