package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.exceptions.NotFoundException;
//import com.rest_api.fs14backend.orderItem.OrderItem;
//import com.rest_api.fs14backend.orderItem.OrderItemController;
//import com.rest_api.fs14backend.orderItem.OrderItemMapper;
//import com.rest_api.fs14backend.orderItem.OrderItemService;
import com.rest_api.fs14backend.orderItem.OrderItem;
import com.rest_api.fs14backend.orderItem.OrderItemController;
import com.rest_api.fs14backend.orderItem.OrderItemService;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemController orderItemController;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Order createOne(@RequestBody OrderRequest orderRequest) {

        Long userId = orderRequest.getOrderDTO().getUserId();



        User user = userService.findById(userId);
        Order orderFromRequest = orderMapper.toOrder(orderRequest.getOrderDTO(), user);
        List<Long> productIds = orderRequest.getProductIds();

        Order order = orderService.createOne(orderFromRequest);

        // do mutations base on id
        for (Long productId : productIds) {
            Product foundProduct = productService.findById(productId);
            if (foundProduct == null) {
                throw new NotFoundException("product not found");
            } else {
                OrderItem orderItem = new OrderItem(1, foundProduct, order);
                orderItemService.createOrderItem(orderItem);
            }
        }
        return order;
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        orderService.deleteOne(id);
    }
}
