package com.nailsSalon.AdriDesign.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findUserEntityByEmail(String email);
    Optional<Customer> findUserEntityById(UUID id);
}
