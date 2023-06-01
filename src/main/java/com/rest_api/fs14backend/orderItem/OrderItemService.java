package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @Transactional
    public OrderItem createOne(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem findById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        if (orderItem == null) {
            throw new NotFoundException("OrderItem not found");
        }
        return orderItem;
    }

    public void deleteById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        if (orderItem == null) {
            throw new NotFoundException("OrderItem not found");
        }
        orderItemRepository.deleteById(id);
    }
}
