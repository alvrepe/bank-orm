package org.iesfm.bank.controllers;

import org.iesfm.bank.Account;
import org.iesfm.bank.Customer;
import org.iesfm.bank.repository.AccountRepository;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/accounts/{iban}")
    public Optional<Account> getAccount(@PathVariable String iban) {
        Optional<Account> account = accountRepository.findById(iban);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no existe la cuenta");
        }
        return account;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/accounts/{iban}")
    public void delete(@PathVariable String iban) {
        if (!accountRepository.existsById(iban)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no existe la cuenta");
        } else {
            accountRepository.deleteById(iban);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{id}/accunts")
    public List<Account> getAccountsByCustomer(@PathVariable int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no existe el cliente");
        } else {
            return accountRepository.findByOwnerId(id);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customers/{id}/accounts")
    public void insert(@RequestBody Account account){
      if (customerRepository.existsById(account.getOwnerId())){
          accountRepository.save(account);
      } else {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "el cliente no existe");
      }
    }
    

}
