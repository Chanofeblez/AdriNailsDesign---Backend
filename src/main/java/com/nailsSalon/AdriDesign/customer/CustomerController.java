package com.nailsSalon.AdriDesign.customer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import com.nailsSalon.AdriDesign.payment.PaymentController;
import com.nailsSalon.AdriDesign.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private JwtUtils jwtUtils;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        return customerService.getCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  @PostMapping("/extract-username")
  public ResponseEntity<?> extractCustomer(@RequestBody Map<String, String> tokenRequest) {
    String token = tokenRequest.get("token");
    if (token == null || token.isEmpty()) {
      return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Token is missing"));
    }

    // Decodificar el token JWT
    DecodedJWT decodedJWT = JWT.decode(token);

    // Extraer el nombre de usuario del token decodificado (en este caso, el email)
    String email = decodedJWT.getSubject();

    // Buscar el cliente en la base de datos utilizando el email
    Optional<Customer> customerOptional = customerService.getCustomerByEmail(email);

    // Si no se encuentra el cliente, retornar un error
    if (!customerOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Customer not found"));
    }

    // Si se encuentra el cliente, devolverlo en la respuesta
    Customer customer = customerOptional.get();
    return ResponseEntity.ok(customer);
  }


  @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            // Encriptamos la contraseña
            customer.setPassword(customerService.encriptPassword(customer.getPassword()));

            // Establecemos la fecha de creación y actualización
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());

            // Creamos el cliente
            Customer createdCustomer = customerService.createCustomer(customer);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customerDetails) {
        try {
            // Actualizamos los detalles del cliente
            Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
            return ResponseEntity.ok(updatedCustomer);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
