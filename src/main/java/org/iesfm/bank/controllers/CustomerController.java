package org.iesfm.bank.controllers;

import org.iesfm.bank.Customer;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/customers")
    public void insert(@RequestBody Customer customer) {
        if (customerRepository.existsByNif(customer.getNif())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        } else {
            customerRepository.save(customer);
        }

    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, path = "/customers/{nif}")
    public void delete(@PathVariable("nif") String nif) {
        if (customerRepository.deleteByNif(nif) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no existe");
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{nif}")
    public Customer getCustomer(@PathVariable("nif") String nif) {
        Customer customer = customerRepository.findOneByNif(nif);
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente");
        }
        return customer;
    }
}
