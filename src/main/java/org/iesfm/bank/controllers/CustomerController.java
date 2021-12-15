package org.iesfm.bank.controllers;

import org.iesfm.bank.Customer;
import org.iesfm.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(method = RequestMethod.DELETE, path = "/customers/{id}")
    public void delete(@PathVariable("id") int id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no existe");
        } else {
            customerRepository.deleteById(id);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/customers/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente");
        }
        return customer;
    }
}
