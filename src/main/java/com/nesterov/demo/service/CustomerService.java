package com.nesterov.demo.service;

import com.nesterov.demo.model.Customer;
import java.util.List;

public interface CustomerService {
    Customer get(long id);
    Customer create(Customer customer);
    Customer update(Customer customer);
    Customer addMoney(long id, long sum);
    void delete(long id);
    List<Customer> getAll();
    Customer addProduct(long productId, long customerId);
    Customer buyProducts(long id);
}
