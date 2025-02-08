package com.example.demo.services;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderServices {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(Long orderId);
    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus orderStatus);
    OrderDTO addProductsToOrder(Long orderId, List<Product> products);
    OrderDTO deleteProductsFormOrder(Long orderId, List<Long> productIds);
    Page<OrderDTO> getAllOrder(Pageable pageable);
    List<OrderDTO> getAllOrderByStatus(OrderStatus orderStatus);
    List<OrderDTO> getOrderByCustomerId(Long customerId);
    List<Product> getProductsByOrderId(Long orderId);
    void deleteOrder(Long orderId);
    void cancelOrder(Long orderNumber);
}
