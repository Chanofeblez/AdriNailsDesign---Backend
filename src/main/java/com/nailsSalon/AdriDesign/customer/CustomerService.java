package com.nailsSalon.AdriDesign.customer;

import com.nailsSalon.AdriDesign.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        // Verificar si existe un cliente con ese id, si no, lanzar una excepción
        Customer customerExistente = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + id));

        return Optional.ofNullable(customerExistente);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer) {

        // Verificar si el email es válido
      if (!checkValidezEmail(customer.getEmail())) {
        throw new InvalidEmailException("Email " + customer.getEmail() + " no es válido.");
      }

        // Verificar si el email ya existe
      if (customerRepository.existsByEmail(customer.getEmail())) {
        throw new EmailAlreadyExistsException("The email " + customer.getEmail() + " already exists.");
      }

      // Verificar si el phoneNumber ya existe
      if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
        throw new PhoneNumberAlreadyExistsException("The phone number " + customer.getPhoneNumber() + " already exists.");
      }

        // Configurar las propiedades de seguridad del cliente
        customer.setEnabled(true);
        customer.setAccountNotExpired(true);
        customer.setAccountNotLocked(true);
        customer.setCredentialNotExpired(true);

        // Guardar el cliente
        Customer customerGuardado = customerRepository.save(customer);

        return customerGuardado;
    }

    public Customer loginCustomer(String email, String password) {
        Customer customerLogin = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("The email/password is incorrect."));

        // Verificar si el password es correcto
        if (!passwordEncoder.matches(password, customerLogin.getPassword())) {
            throw new IllegalArgumentException("The email/password is incorrect.");
        }

        return customerLogin;
    }

    public Customer updateCustomer(UUID id, Customer customerDetails) {

        // Verificar si existe miembro con ese id, si no, lanzar una excepción
        Customer customerExistente = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Miembro con ese id no existe, id: " + id));

        // Verificar si el email es válido
        if (!checkValidezEmail(customerDetails.getEmail())) {
            throw new IllegalArgumentException("Email " + customerDetails.getEmail() + " no es válido");
        }

        // Verificar si el email que se quiere actualizar ya existe
        if (!customerDetails.getEmail().equals(customerExistente.getEmail()) && customerRepository.existsByEmail(customerDetails.getEmail())) {
            throw new IllegalArgumentException("Email " + customerDetails.getEmail() + " ya está registrado.");
        }

        // Actualizar las propiedades del cliente
        customerExistente.setName(customerDetails.getName());
        customerExistente.setEmail(customerDetails.getEmail());
        //customerExistente.setPassword(customerDetails.getPassword());
        customerExistente.setPhoneNumber(customerDetails.getPhoneNumber());
        customerExistente.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customerExistente);
    }

    public void deleteCustomer(UUID id) {
        // Verificar si el id existe, si no, lanzar una excepción
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Miembro con id " + id + " no existe");
        }

        customerRepository.deleteById(id);
    }

    private boolean checkValidezEmail(String email) {
        return Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9,-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        ).asPredicate().test(email);
    }

    public String encriptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

