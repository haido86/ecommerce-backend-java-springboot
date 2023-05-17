package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return order;
    }

    public Order createOne(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOne(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
