package com.nesterov.demo.service;

import static java.lang.Long.compare;
import static java.lang.Math.abs;
import static java.lang.String.format;

import com.nesterov.demo.exception.NotEnoughFundsException;
import com.nesterov.demo.exception.NotFoundException;
import com.nesterov.demo.model.Customer;
import com.nesterov.demo.model.DBEntity;
import com.nesterov.demo.model.Discount;
import com.nesterov.demo.model.Product;
import com.nesterov.demo.repository.CustomerRepository;
import com.nesterov.demo.repository.DiscountRepository;
import com.nesterov.demo.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private DiscountRepository discountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository, DiscountRepository discountRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public Customer get(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            String message = format("Product with id = '%s' not found", id);
            throw new NotFoundException(message);
        }
    }

    @Override
    public Customer buyProducts(long id) {
        Customer customer = (Customer) verifyIsPresent(customerRepository, id);
        double discount = customer.getProducts().stream()
                .filter(p -> p.getDiscount() != null)
                .distinct()
                .sorted((p1, p2) -> compare(p2.getDiscount().getLevel(), p1.getDiscount().getLevel()))
                .limit(3).mapToDouble(product -> calculateDiscount(product.getPrice(), product.getDiscount().getLevel())).sum();
        double generalSum = customer.getProducts().stream().mapToDouble(Product::getPrice).sum();
        customer.setAmount(customer.getAmount() - (generalSum - discount));
        customer.setProducts(null);
        return customer;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());
        if (customerOptional.isPresent()) {
            Customer customerUpdate = customerOptional.get();
            customerUpdate.setId(customer.getId());
            customerUpdate.setFirstName(customer.getFirstName());
            customerUpdate.setLastName(customer.getLastName());
            customerUpdate.setEmail(customer.getEmail());
            customerUpdate.setPhone(customer.getPhone());
            customerUpdate.setAmount(customer.getAmount());
            customerUpdate.setProducts(customer.getProducts());
            customerRepository.save(customerUpdate);
            return customerUpdate;
        } else {
            String message = format("Customer with id = '%s' not found", customer.getId());
            throw new NotFoundException(message);
        }
    }

    @Override
    public Customer addMoney(long id, long sum) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer updatedCustomer = customerOptional.get();
            double newAmount = updatedCustomer.getAmount() + abs(sum);
            updatedCustomer.setAmount(newAmount);
            customerRepository.save(updatedCustomer);
            return updatedCustomer;
        } else {
            String message = format("Customer with id = '%s' not found", id);
            throw new NotFoundException(message);
        }
    }

    @Override
    public void delete(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        }
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addProduct(long productId, long customerId) {
        Product product = (Product) verifyIsPresent(productRepository, productId);
        Customer customer = (Customer) verifyIsPresent(customerRepository, customerId);
        verifyIsEnoughFounds(customer, product);
        customer.getProducts().add(product);
        update(customer);
        return customer;
    }

    private DBEntity verifyIsPresent(JpaRepository jpaRepository, long id) {
        Optional<DBEntity> optional = jpaRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            String message = format("'%s' with id = '%s' not found", jpaRepository.getClass(), id);
            throw new NotFoundException(message);
        }
    }

    private void verifyIsEnoughFounds(Customer customer, Product product) {
        double generalSum = customer.getProducts().stream().mapToDouble(Product::getPrice).sum();
        if (product.getDiscount() != null) {
            Discount discount = (Discount) verifyIsPresent(discountRepository, product.getDiscount().getId());
            if (customer.getAmount() < generalSum + product.getPrice() - calculateDiscount(product.getPrice(), discount.getLevel())) {
                throw new NotEnoughFundsException("Not enough funds in the account");
            }
        } else {
            if (customer.getAmount() < generalSum + product.getPrice()) {
                throw new NotEnoughFundsException("Not enough funds in the account");
            }
        }
    }

    private double calculateDiscount(double price, double discount) {
        return price / 100 * discount;
    }
}
