package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.cart.CartItem;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.orderItem.OrderItem;
import com.rest_api.fs14backend.orderItem.OrderItemService;
import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderMapper orderMapper;

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

    public Order createOne(OrderRequest orderRequest) throws Exception{
        try {
            //check if user from orderRequest exist or not
            Long userId = orderRequest.getUserId();
            User user = userService.findById(userId);
            //get the list of products from orderRequest with productId and quantity
            List<CartItem> cartItemList = orderRequest.getCartItemList();

            // Create new orderItemList for new order, later will add the items from cart to it
            List<OrderItem> orderItemList = new ArrayList<>();

            // Create orderDTO from orderRequest
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUserId(user.getId());

            //Create order if user exist
            Order orderFromRequest = orderMapper.toOrder(orderDTO, user);
            Order order = orderRepository.save(orderFromRequest);

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

    public void deleteOne(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
