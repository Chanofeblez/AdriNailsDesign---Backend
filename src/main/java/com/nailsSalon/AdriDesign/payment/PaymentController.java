package com.nailsSalon.AdriDesign.payment;

import com.nailsSalon.AdriDesign.course.CourseService;
import com.nailsSalon.AdriDesign.dto.PaymentRequestDTO;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.Money;
import com.squareup.square.models.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final SquarePaymentService squarePaymentService;
    @Autowired
    private CourseService courseService;


    @Autowired
    public PaymentController(SquarePaymentService squarePaymentService) {
        this.squarePaymentService = squarePaymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        if (paymentRequest.getSourceId() == null || paymentRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Invalid payment details");
        }

        try {
            Money amountMoney = new Money.Builder()
                    .amount(paymentRequest.getAmount()) // amount in cents
                    .currency("USD")
                    .build();

            String idempotencyKey = UUID.randomUUID().toString();

            SalonPayment payment = squarePaymentService.createPayment(
                    paymentRequest.getSourceId(),
                    idempotencyKey,
                    amountMoney,
                    paymentRequest.getCustomerId(),
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

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyPayment(@RequestParam UUID courseId, @RequestParam UUID userId) {
        boolean hasPaid = courseService.verifyPayment(courseId, userId);
        return ResponseEntity.ok(hasPaid);
    }
}
