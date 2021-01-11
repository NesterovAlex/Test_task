package com.nesterov.demo.service;

import static java.lang.String.format;

import com.nesterov.demo.exception.InCorrectDiscountLevelException;
import com.nesterov.demo.exception.NotFoundException;
import com.nesterov.demo.model.Discount;
import com.nesterov.demo.repository.DiscountRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService{

    private DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount get(long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isPresent()) {
            return discount.get();
        } else {
            String message = format("Discount with id = '%s' not found", id);
            throw new NotFoundException(message);
        }
    }

    @Override
    public Discount create(Discount discount) {
        if(discount.getLevel() > 100){
            String message = format("level of discount = '%s' is incorrect. It must be not more 100", discount.getLevel());
            throw new InCorrectDiscountLevelException(message);
        }
        return discountRepository.save(discount);
    }

    @Override
    public Discount update(Discount discount) {
        Optional<Discount> discountOptional = discountRepository.findById(discount.getId());
        if (discountOptional.isPresent()) {
            Discount discountUpdate = discountOptional.get();
            discountUpdate.setId(discount.getId());
            discountUpdate.setLevel(discount.getLevel());
            return discountUpdate;
        } else {
            String message = format("Discount with id = '%s' not found", discount.getId());
            throw new NotFoundException(message);
        }
    }

    @Override
    public void delete(long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isPresent()) {
            discountRepository.delete(discount.get());
        }
    }

    @Override
    public List<Discount> getAll() {
        return discountRepository.findAll();
    }
}
