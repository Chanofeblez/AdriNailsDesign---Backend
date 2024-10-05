package com.nailsSalon.AdriDesign.serviciovariant;

import com.nailsSalon.AdriDesign.customer.CustomerService;
import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-variants")
public class ServicioVariantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioVariantController.class);

    private final ServicioVariantService serviceVariantService;

    @Autowired
    public ServicioVariantController(ServicioVariantService serviceVariantService) {
        this.serviceVariantService = serviceVariantService;
    }

    @GetMapping
    public List<ServicioVariant> getAllServiceVariants() {
        return serviceVariantService.getAllServiceVariants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioVariant> getServiceVariantById(@PathVariable UUID id) {
        Optional<ServicioVariant> serviceVariant = serviceVariantService.getServiceVariantById(id);
        return serviceVariant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/by-ids")
    public List<ServicioVariant> getServiceVariantsByIds(@RequestBody List<UUID> ids) {
        return serviceVariantService.getServiceVariantsByIds(ids);
    }

    @PostMapping
    public ServicioVariant createServiceVariant(@RequestBody ServicioVariant serviceVariant) {
        LOGGER.info("Creando ServicioVariant {}", serviceVariant);
        return serviceVariantService.createServiceVariant(serviceVariant);
    }

  @PostMapping("/list")
  public List<ServicioVariant> createServiceVariants(@RequestBody List<ServicioVariant> serviceVariants) {
    LOGGER.info("Creando lista de ServicioVariants {}", serviceVariants);
    return serviceVariantService.createServiceVariants(serviceVariants);
  }


  @PutMapping("/{id}")
    public ResponseEntity<ServicioVariant> updateServiceVariant(@PathVariable UUID id, @RequestBody ServicioVariant serviceVariantDetails) {
        try {
            ServicioVariant updatedServiceVariant = serviceVariantService.updateServiceVariant(id, serviceVariantDetails);
            return ResponseEntity.ok(updatedServiceVariant);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceVariant(@PathVariable UUID id) {
        serviceVariantService.deleteServiceVariant(id);
        return ResponseEntity.noContent().build();
    }

}
