package org.iesfm.bank.repository;

import org.iesfm.bank.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByNif(String nif);
}
