package org.iesfm.bank.repository;

import org.iesfm.bank.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByOwnerId(int id);
}
