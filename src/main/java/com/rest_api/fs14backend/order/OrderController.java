package com.rest_api.fs14backend.order;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Transactional
    @PostMapping
    public Order createOne(@RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.createOne(orderRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        orderService.deleteOne(id);
    }
}
