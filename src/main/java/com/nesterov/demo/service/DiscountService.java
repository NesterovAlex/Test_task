package com.nesterov.demo.service;

import com.nesterov.demo.model.Discount;
import java.util.List;

public interface DiscountService {
    Discount get(long id);
    Discount create(Discount discount);
    Discount update(Discount discount);
    void delete(long id);
    List<Discount> getAll();
}
