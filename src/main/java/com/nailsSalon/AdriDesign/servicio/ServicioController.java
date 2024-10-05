package com.nailsSalon.AdriDesign.servicio;

import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServicioController {

    private final ServicioService serviceService;

    @Autowired
    public ServicioController(ServicioService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<Servicio> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServiceById(@PathVariable UUID id) {
        Optional<Servicio> service = serviceService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name")
    public ResponseEntity<Servicio> getServicioByName(@RequestParam String name) {
        System.out.println("name receiv in Controller: " + name);
        return serviceService.getServicioByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Servicio createService(@RequestBody Servicio service) {
        return serviceService.createService(service);
    }

  @PostMapping("/list")
  public List<Servicio> createServices(@RequestBody List<Servicio> services) {
    return serviceService.createServices(services);
  }


  @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateService(@PathVariable UUID id, @RequestBody Servicio serviceDetails) {
        try {
            Servicio updatedService = serviceService.updateService(id, serviceDetails);
            return ResponseEntity.ok(updatedService);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
