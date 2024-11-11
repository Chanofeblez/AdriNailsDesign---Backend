package com.nailsSalon.AdriDesign.payment;

import com.nailsSalon.AdriDesign.appointment.AppointmentService;
import com.nailsSalon.AdriDesign.course.Course;
import com.nailsSalon.AdriDesign.course.CourseService;
import com.nailsSalon.AdriDesign.course.CustomerCourse;
import com.nailsSalon.AdriDesign.course.CustomerCourseRepository;
import com.nailsSalon.AdriDesign.customer.Customer;
import com.nailsSalon.AdriDesign.customer.CustomerService;
import com.nailsSalon.AdriDesign.dto.PaymentRequestDTO;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.Money;
import com.squareup.square.models.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final SquarePaymentService squarePaymentService;
    @Autowired
    private AppointmentService appointmentService;  // Si ya tienes un servicio para Appointment
    @Autowired
    private CourseService courseService;  // Para manejar los cursos
  @Autowired
  private CustomerService customerService;  // Para manejar los customers
  @Autowired
  private CustomerCourseRepository customerCourseRepository;  // Para manejar los customers


    @Autowired
    public PaymentController(SquarePaymentService squarePaymentService) {
        this.squarePaymentService = squarePaymentService;
    }

    @PostMapping("/charge-appointment")
    public ResponseEntity<?> processAppointmentPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        if (paymentRequest.getSourceId() == null || paymentRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Invalid payment details");
        }
        logger.info("PaymentRequest for Appointment: {}", paymentRequest);

        try {
            Money amountMoney = new Money.Builder()
                    .amount(paymentRequest.getAmount()) // amount in cents
                    .currency("USD")
                    .build();

            String idempotencyKey = UUID.randomUUID().toString();

            SalonPayment payment = squarePaymentService.createAppointmentPayment(
                    paymentRequest.getSourceId(),
                    idempotencyKey,
                    amountMoney,
                    paymentRequest.getCustomerId().toString(),
                    paymentRequest.getLocationId(),
                    paymentRequest.getAppointmentId()
            );
            return ResponseEntity.ok(payment);
        } catch (ApiException e) {

            return ResponseEntity.status(e.getResponseCode()).body(e.getErrors());
        } catch (IOException e) {

            return ResponseEntity.status(500).body("Payment processing failed due to a network error.");
        }
    }

    // Endpoint para procesar pagos de Cursos
    @PostMapping("/charge-course")
    public ResponseEntity<?> processCoursePayment(@RequestBody PaymentRequestDTO paymentRequest) {
        if (paymentRequest.getSourceId() == null || paymentRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Invalid payment details");
        }
        logger.info("PaymentRequest for Courseeee: {}", paymentRequest);
      logger.info("sourceId: {}", paymentRequest.getSourceId());
      logger.info("amount: {}", paymentRequest.getAmount());
      logger.info("customerId: {}", paymentRequest.getCustomerId());
      logger.info("locationId: {}", paymentRequest.getLocationId());
      logger.info("courseId: {}", paymentRequest.getCourseId());


      try {
        logger.info("Dentro del Try: {}");
            Money amountMoney = new Money.Builder()
                    .amount(paymentRequest.getAmount()) // cantidad en centavos
                    .currency("USD")
                    .build();

            String idempotencyKey = UUID.randomUUID().toString();

        logger.info("idempotencyKey: {}", idempotencyKey);

            SalonPayment payment = squarePaymentService.createCoursePayment(
                    paymentRequest.getSourceId(),
                    idempotencyKey,
                    amountMoney,
                    paymentRequest.getCustomerId(),
                    paymentRequest.getLocationId(),
                    paymentRequest.getCourseId()
            );

        logger.info("payment: {}", payment);

          // Lógica adicional para crear la relación en CustomerCourse
          CustomerCourse customerCourse = new CustomerCourse();
          Optional<Customer> customerOptional = customerService.getCustomerById(paymentRequest.getCustomerId());
          if (customerOptional.isPresent()) {
            customerCourse.setCustomer(customerOptional.get()); // Establecemos el valor del Customer
          } else {
            throw new IllegalArgumentException("Customer not found");
          }
          Optional<Course> courseOptional = courseService.getCourseById(paymentRequest.getCourseId());
          if (courseOptional.isPresent()) {
            customerCourse.setCourse(courseOptional.get());
          } else {
            throw new IllegalArgumentException("Course not found");
          }
          customerCourse.setPaymentStatus(true);  // Marcamos como pagado

          // Guardamos la relación en la base de datos
          customerCourseRepository.save(customerCourse);

            return ResponseEntity.ok(payment);
        } catch (ApiException e) {
        logger.error("Square API Exception: ", e);
            return ResponseEntity.status(e.getResponseCode()).body(e.getErrors());
        } catch (IOException e) {
        logger.error("General Exception: ", e);
            return ResponseEntity.status(500).body("Payment processing failed due to a network error.");
        }
    }

    // Verificar si un usuario ha pagado un curso
    @GetMapping("/verify-course-payment")
    public ResponseEntity<Boolean> verifyCoursePayment(@RequestParam UUID courseId, @RequestParam UUID userId) {
        boolean hasPaid = courseService.verifyPayment(courseId, userId);
        return ResponseEntity.ok(hasPaid);
    }

    @GetMapping("/verify-appointment-payment")
    public ResponseEntity<Boolean> verifyAppointmentPayment(@RequestParam UUID appointmentId, @RequestParam UUID userId) {
        boolean hasPaid = appointmentService.verifyPayment(appointmentId, userId);
        return ResponseEntity.ok(hasPaid);
    }

}
