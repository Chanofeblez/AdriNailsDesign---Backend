package com.nailsSalon.AdriDesign.payment;

import com.nailsSalon.AdriDesign.appointment.Appointment;
import com.nailsSalon.AdriDesign.appointment.AppointmentRepository;
import com.nailsSalon.AdriDesign.course.Course;
import com.nailsSalon.AdriDesign.course.CourseRepository;
import com.nailsSalon.AdriDesign.customer.CustomerRepository;
import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.api.PaymentsApi;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.CreatePaymentRequest;
import com.nailsSalon.AdriDesign.customer.Customer;
import com.squareup.square.models.Money;
import com.squareup.square.models.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class SquarePaymentService {

  private static final Logger logger = LoggerFactory.getLogger(SquarePaymentService.class);

    private final SquareClient squareClient;
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;
    private final SalonPaymentRepository salonPaymentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public SquarePaymentService(@Value("${square.access.token}") String accessToken,
                                CustomerRepository customerRepository,
                                AppointmentRepository appointmentRepository,
                                SalonPaymentRepository salonPaymentRepository,
                                CourseRepository courseRepository) {
        this.squareClient = new SquareClient.Builder()
                .accessToken(accessToken)
                .environment(Environment.PRODUCTION) // Cambia a "production" para producción
                .build();
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
        this.salonPaymentRepository = salonPaymentRepository;
        this.courseRepository = courseRepository;
    }

    public SquareClient getSquareClient() {
        return squareClient;
    }

    public SalonPayment createAppointmentPayment(String sourceId, String idempotencyKey, Money amountMoney, String customerId, String locationId, UUID appointmentId) throws ApiException, IOException {
        PaymentsApi paymentsApi = squareClient.getPaymentsApi();

        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(sourceId, idempotencyKey)
                .amountMoney(amountMoney)
                .autocomplete(true)
                .customerId(customerId)
                .locationId(locationId)
                .referenceId("123456")
                .build();

        com.squareup.square.models.Payment squarePayment = paymentsApi.createPayment(createPaymentRequest).getPayment();

        // Cargar los objetos Customer y Appointment desde la base de datos
        Customer customer = customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Guardar el pago en la base de datos
        SalonPayment salonPayment = new SalonPayment();
        salonPayment.setCustomer(customer);
        salonPayment.setAppointment(appointment);
        salonPayment.setPaymentMethodId(sourceId);
        salonPayment.setAmount(amountMoney.getAmount());
        salonPayment.setCurrency(amountMoney.getCurrency());
        salonPayment.setStatus(SalonPayment.PaymentStatus.COMPLETED);

        return salonPaymentRepository.save(salonPayment);
    }

    public SalonPayment createCoursePayment(String sourceId, String idempotencyKey, Money amountMoney, UUID customerId, String locationId, UUID courseId) throws ApiException, IOException {
        PaymentsApi paymentsApi = squareClient.getPaymentsApi();

      logger.info("Service-idempotencyKey: {}", idempotencyKey);
      logger.info("Service-sourceId: {}", sourceId);
      logger.info("Service-amount: {}", amountMoney);
      logger.info("Service-customerId: {}", customerId);
      logger.info("Service-locationId: {}", locationId);
      logger.info("Service-courseId: {}", courseId);

        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(sourceId, idempotencyKey)
                .amountMoney(amountMoney)
                .autocomplete(true)
                .customerId(customerId.toString())
                .locationId(locationId)
                .referenceId("123456")
                .build();

        com.squareup.square.models.Payment squarePayment = paymentsApi.createPayment(createPaymentRequest).getPayment();

        // Cargar el objeto Customer desde la base de datos
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Cargar el Curso desde la base de datos
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Utilizar el constructor para crear el SalonPayment
        SalonPayment salonPayment = new SalonPayment(
                customer,
                course,
                sourceId,
                amountMoney.getAmount(),
                amountMoney.getCurrency(),
                SalonPayment.PaymentStatus.COMPLETED
        );

        return salonPaymentRepository.save(salonPayment);
    }


}

