package com.nailsSalon.AdriDesign.appointment;

import com.nailsSalon.AdriDesign.customer.Customer;
import com.nailsSalon.AdriDesign.customer.CustomerRepository;
import com.nailsSalon.AdriDesign.dto.AppointmentRequestDTO;
import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import com.nailsSalon.AdriDesign.reservedslot.ReservedSlot;
import com.nailsSalon.AdriDesign.reservedslot.ReservedSlotRepository;
import com.nailsSalon.AdriDesign.reservedslot.ReservedSlotService;
import com.nailsSalon.AdriDesign.servicio.Servicio;
import com.nailsSalon.AdriDesign.servicio.ServicioRepository;
import com.nailsSalon.AdriDesign.serviciovariant.ServicioVariant;
import com.nailsSalon.AdriDesign.serviciovariant.ServicioVariantRepository;
import com.nailsSalon.AdriDesign.twilio.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private ReservedSlotService reservedSlotService;

    @Autowired
    private SMSService smsService;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;

    private final ServicioRepository servicioRepository;

    private final ServicioVariantRepository servicioVariantRepository;
    private final ReservedSlotRepository reservedSlotRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, CustomerRepository customerRepository,
                              ServicioRepository servicioRepository, ServicioVariantRepository servicioVariantRepository,
                              ReservedSlotRepository reservedSlotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.servicioRepository = servicioRepository;
        this.servicioVariantRepository = servicioVariantRepository;
        this.reservedSlotRepository = reservedSlotRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(UUID id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByUserEmail(String userEmail) {
        logger.info("user2", userEmail);
        List<Appointment> appointmentResp = appointmentRepository.findByCustomerEmail(userEmail);
        logger.info("appointmentResp: {}", appointmentResp);
        return appointmentRepository.findByCustomerEmail(userEmail);
    }

    public Appointment createAppointment(String customerEmail, String serviceName, List<UUID> variantIds,
                                         String date, String time, BigDecimal totalCost,
                                         AppointmentStatus status, String imagePath) {
        logger.info("Dentro del servicio: {}", customerEmail, serviceName, variantIds, date, time, totalCost,
                 status, imagePath);
        Appointment appointment = new Appointment();
        logger.info("Dentro del servicio2");

        List<String> uuidStrings = variantIds.stream()
                .map(UUID::toString)
                .collect(Collectors.toList());

        String timeString = time; // convierte el número a "03:00"

        // Asignar los valores
        appointment.setCustomerEmail(customerEmail);
        logger.info("email: {}", appointment.getCustomerEmail());
        appointment.setServiceName(serviceName);
        logger.info("servicio: {}", appointment.getServiceName());
        appointment.setServiceVariantIds(uuidStrings);
        logger.info("variantes: {}", appointment.getServiceVariantIds());
        appointment.setAppointmentDate(LocalDate.parse(date));
        logger.info("date: {}", appointment.getAppointmentDate());
        appointment.setAppointmentTime(LocalTime.parse(timeString));
        logger.info("time: {}", appointment.getAppointmentTime());
        appointment.setTotalCost(totalCost);
        logger.info("costo: {}", appointment.getTotalCost());
        appointment.setStatus(status);
        logger.info("status: {}", appointment.getStatus());
        appointment.setImagePath(imagePath);
        logger.info("imagePath: {}", appointment.getImagePath());

        logger.info("AppointmentInService: {}", appointment);

        ReservedSlot reservedSlot = new ReservedSlot();
        reservedSlot.setDate(appointment.getAppointmentDate());
        reservedSlot.setTime(appointment.getAppointmentTime());

        reservedSlotRepository.save(reservedSlot);

      Appointment savedAppointment = appointmentRepository.save(appointment);

      // Obtener el cliente por su email
      Optional<Customer> customerOpt = customerRepository.findByEmail(customerEmail);
      String customerName = customerOpt.map(Customer::getName).orElse("Cliente");

      // Crear el mensaje personalizado
      String message = customerName + " ha reservado un nuevo appointment el " + date + " a las " + time + ".";

      // Enviar SMS
      smsService.sendSms("+13058340807", message); // Número de teléfono de tu esposa

      return savedAppointment;
    }

    public Appointment updateAppointment(UUID id, AppointmentRequestDTO appointmentRequestDTO) {
        return appointmentRepository.findById(id).map(appointment -> {
            // Actualizar la fecha y hora
                appointment.setAppointmentDate(LocalDate.parse(appointmentRequestDTO.getAppointmentDate()));
                appointment.setAppointmentTime(LocalTime.parse(appointmentRequestDTO.getAppointmentTime()));
                appointment.setTotalCost(appointmentRequestDTO.getTotalCost());

            // Actualizar el servicio si se proporcionó un nuevo ID de servicio
            if (appointmentRequestDTO.getServiceName() != null) {
                Servicio service = servicioRepository.findByName(appointmentRequestDTO.getServiceName())
                        .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + appointmentRequestDTO.getServiceName()));
                appointment.setServiceName(appointmentRequestDTO.getServiceName());
            }

            // Actualizar los variantes de servicio si se proporcionaron
            if (appointmentRequestDTO.getServiceVariantIds() != null && !appointmentRequestDTO.getServiceVariantIds().isEmpty()) {
                List<ServicioVariant> serviceVariants = servicioVariantRepository.findAllById(appointmentRequestDTO.getServiceVariantIds());
               // appointment.setServiceVariants(serviceVariants);
            }

            // Llama al método para liberar el slot
            reservedSlotService.releaseSlot(appointment.getAppointmentDate(), appointment.getAppointmentTime());

            // Guardar y devolver la cita actualizada
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
    }


    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public Appointment updateAppointmentStatus(UUID appointmentId, AppointmentStatus status) {

        // Buscar la cita en la base de datos
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));

        logger.info("AppointmentStatus: {}", appointment.getStatus());
        if ((appointment.getStatus() == AppointmentStatus.UPCOMING || appointment.getStatus() == AppointmentStatus.CONFIRMED
           || appointment.getStatus() == AppointmentStatus.PENDING) && status == AppointmentStatus.CANCELED){
            // Llama al método para liberar el slot
            reservedSlotService.releaseSlot(appointment.getAppointmentDate(), appointment.getAppointmentTime());
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELED && status == AppointmentStatus.CONFIRMED){
            // Llama al método para liberar el slot
            ReservedSlot reservedSlot = new ReservedSlot();
            reservedSlot.setDate(appointment.getAppointmentDate());
            reservedSlot.setTime(appointment.getAppointmentTime());

            reservedSlotRepository.save(reservedSlot);
        }

        // Actualizar el estado
        appointment.setStatus(status);

        // Guardar la cita actualizada en la base de datos
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        System.out.println("Dentro del servicio");
        logger.info("AppointmentInService: {}", appointment.getId());
        logger.info("AppointmentInService: {}", appointment.getServiceName());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        logger.info("Appointment saved successfully: {}", savedAppointment.getId());

        return savedAppointment;
    }

    public boolean verifyPayment(UUID appointmentId, UUID userId) {
        // Buscar el appointment por su ID y el ID del usuario
       // Optional<Appointment> appointment = appointmentRepository.findByIdAndUserId(appointmentId, userId);

        // Verificar si la cita existe y si el estado del pago es "pagado"
       // if (appointment.isPresent() && appointment.get().getPaymentStatus().equals("PAID")) {
       //     return true;  // Si ha sido pagado, devuelve true
       // }

        return false;  // Si no ha sido pagado o no existe, devuelve false
    }

     @Scheduled(cron = "0 0 0 * * ?")  // Ejecuta la tarea todos los días a la medianoche
     public void updateAppointmentsStatusToCompletedScheduler() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> updatedAppointments = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (Appointment appointment : appointments) {
            if (appointment.getStatus() != AppointmentStatus.COMPLETED &&
                    appointment.getStatus() != AppointmentStatus.CANCELED &&
                    appointment.getAppointmentDate().isBefore(currentDate)) {

                // Cambiar el estado a COMPLETED si la fecha ya ha pasado
                appointment.setStatus(AppointmentStatus.COMPLETED);
                updatedAppointments.add(appointment);
            }
        }
        if (!updatedAppointments.isEmpty()) {
            appointmentRepository.saveAll(updatedAppointments);
        }
     }

}
