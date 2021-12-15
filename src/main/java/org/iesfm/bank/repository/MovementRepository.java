package org.iesfm.bank.repository;

import org.iesfm.bank.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
}
