package com.nesterov.demo.controller;

import com.nesterov.demo.model.Discount;
import com.nesterov.demo.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscountController {

    private DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/discounts/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable long id) {
        return ResponseEntity.ok().body(discountService.get(id));
    }

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        return ResponseEntity.ok().body(discountService.getAll());
    }

    @PostMapping("/create_discount")
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        return ResponseEntity.ok().body(discountService.create(discount));
    }

    @PutMapping("/discounts/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable long id, @RequestBody Discount discount) {
        discount.setId(id);
        return ResponseEntity.ok().body(discountService.update(discount));
    }

    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Discount> deleteDiscount(@PathVariable long id) {
        discountService.delete(id);
        return (ResponseEntity<Discount>) ResponseEntity.status(HttpStatus.OK);
    }
}
