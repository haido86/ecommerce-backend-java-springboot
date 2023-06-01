package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.cart.CartItem;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.orderItem.OrderItem;
import com.rest_api.fs14backend.orderItem.OrderItemService;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
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

    @Transactional
    @PostMapping
    public Order createOne(@RequestBody OrderRequest orderRequest) throws Exception {
        try {
            //check if user from orderRequest exist or not
            Long userId = orderRequest.getOrderDTO().getUserId();
            User user = userService.findById(userId);
            //get the list of products from orderRequest with productId and quantity
            List<CartItem> cartItemList = orderRequest.getCartItemList();

            // Create new orderItemList for new order, later will add the items from cart to it
            List<OrderItem> orderItemList = new ArrayList<>();

            //Create order if user exist
            Order orderFromRequest = orderMapper.toOrder(orderRequest.getOrderDTO(), user);
            Order order = orderService.createOne(orderFromRequest);

            //check if all the products in the list of the orderRequest exist, if exists, create orderItem link
            //to new created order

            for (CartItem cartItem : cartItemList) {
                Product foundProduct = productService.findById(cartItem.getProductId());
                int quantity = cartItem.getQuantity();
                if (foundProduct == null) {
                    throw new NotFoundException("product not found");
                } else if (quantity > foundProduct.getInventory().getQuantity()) {
                    quantity = foundProduct.getInventory().getQuantity();
                }

                OrderItem orderItem = new OrderItem(quantity, foundProduct, order);
                orderItemService.createOne(orderItem);
                orderItemList.add(orderItem);
            }
            // Set the orderList to the array of items
            order.setOrderItemList(orderItemList);

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("cannot process");
        }

    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        orderService.deleteOne(id);
    }
}
