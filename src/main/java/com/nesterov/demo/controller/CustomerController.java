package com.nesterov.demo.controller;

import com.nesterov.demo.model.Customer;
import com.nesterov.demo.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        return ResponseEntity.ok().body(customerService.get(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @PostMapping("/create_user")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok().body(customerService.create(customer));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok().body(customerService.update(customer));
    }

    @PutMapping("/add_money")
    public ResponseEntity<Customer> addMoney(@RequestParam long id, @RequestParam String name, long sum) {
        return ResponseEntity.ok().body(customerService.addMoney(id, sum));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable long id) {
        customerService.delete(id);
        return (ResponseEntity<Customer>) ResponseEntity.status(HttpStatus.OK);
    }

    @PutMapping("/pay")
    public ResponseEntity<Customer> addProductToCustomer(@RequestParam long productId, @RequestParam long customerId) {
        return ResponseEntity.ok().body(customerService.addProduct(productId,customerId));
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<Customer> buyProducts(@PathVariable long id) {
        return ResponseEntity.ok().body(customerService.buyProducts(id));
    }
}
