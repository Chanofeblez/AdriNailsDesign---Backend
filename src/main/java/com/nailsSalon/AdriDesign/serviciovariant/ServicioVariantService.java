package com.nailsSalon.AdriDesign.serviciovariant;

import com.nailsSalon.AdriDesign.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicioVariantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioVariantController.class);

    private final ServicioVariantRepository serviceVariantRepository;

    @Autowired
    public ServicioVariantService(ServicioVariantRepository serviceVariantRepository) {
        this.serviceVariantRepository = serviceVariantRepository;
    }

    public List<ServicioVariant> getAllServiceVariants() {
        return serviceVariantRepository.findAll();
    }

    public Optional<ServicioVariant> getServiceVariantById(UUID id) {
        return serviceVariantRepository.findById(id);
    }

    public List<ServicioVariant> getServiceVariantsByIds(List<UUID> ids) {
        return serviceVariantRepository.findAllById(ids);
    }

    public ServicioVariant createServiceVariant(ServicioVariant serviceVariant) {
        LOGGER.info("Creando ServicioVariant {}", serviceVariant);
        return serviceVariantRepository.save(serviceVariant);
    }

  // Método para guardar una lista de variantes
  public List<ServicioVariant> createServiceVariants(List<ServicioVariant> serviceVariants) {
    // Iterar y guardar cada objeto en la base de datos
    return serviceVariantRepository.saveAll(serviceVariants);
  }

    public ServicioVariant updateServiceVariant(UUID id, ServicioVariant serviceVariantDetails) {
        return serviceVariantRepository.findById(id).map(serviceVariant -> {
            serviceVariant.setName(serviceVariantDetails.getName());
            serviceVariant.setDescription(serviceVariantDetails.getDescription());
            serviceVariant.setPrice(serviceVariantDetails.getPrice());
            return serviceVariantRepository.save(serviceVariant);
        }).orElseThrow(() -> new ResourceNotFoundException("ServiceVariant not found with id " + id));
    }

    public void deleteServiceVariant(UUID id) {
        serviceVariantRepository.deleteById(id);
    }

}
