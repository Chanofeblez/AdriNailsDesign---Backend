package com.nailsSalon.AdriDesign.servicio;

import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicioService {


    private final ServicioRepository servicioRepository;

    @Autowired
    public ServicioService(ServicioRepository serviceRepository) {
        this.servicioRepository = serviceRepository;
    }

    public List<Servicio> getAllServices() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> getServiceById(UUID id) {
        return servicioRepository.findById(id);
    }

    public Optional<Servicio> getServicioByName(String name) {
        System.out.println("name receiv in Service: " + name);
        return servicioRepository.findByName(name);
    }

    public Servicio createService(Servicio service) {
        // Validación de negocio antes de guardar
        validateService(service);
        return servicioRepository.save(service);
    }

  public List<Servicio> createServices(List<Servicio> services) {
    return servicioRepository.saveAll(services);
  }

    public Servicio updateService(UUID id, Servicio serviceDetails) {
        return servicioRepository.findById(id).map(service -> {
            validateService(serviceDetails); // Validación de negocio
            service.setName(serviceDetails.getName());
            service.setDescription(serviceDetails.getDescription());
            service.setPrice(serviceDetails.getPrice());
            service.setType(serviceDetails.getType());
            service.setImagePath(serviceDetails.getImagePath());
            service.setNote(serviceDetails.getNote());
            return servicioRepository.save(service);
        }).orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + id));
    }

    public void deleteService(UUID id) {
        servicioRepository.deleteById(id);
    }

    private void validateService(Servicio service) {
        // Ejemplo de validación: Verificar que el precio no sea negativo
        if (service.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        // Puedes agregar más validaciones según las necesidades del negocio
    }
}
