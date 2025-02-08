package com.example.demo.controllers;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import com.example.demo.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderServices.createOrder(orderDTO));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId,
                                                @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderServices.updateOrder(orderId, orderDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderServices.getOrderById(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderServices.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderServices.getAllOrder(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderServices.getAllOrderByStatus(status));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderServices.getOrderByCustomerId(customerId));
    }


    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderServices.updateOrderStatus(orderId, status));
    }


    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderServices.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{orderId}/products")
    public ResponseEntity<OrderDTO> addProductsToOrder(@PathVariable Long orderId,
                                                       @RequestBody List<Product> products) {
        return ResponseEntity.ok(orderServices.addProductsToOrder(orderId, products));
    }


    @DeleteMapping("/{orderId}/products")
    public ResponseEntity<OrderDTO> removeProductsFromOrder(@PathVariable Long orderId,
                                                            @RequestBody List<Long> productIds) {
        return ResponseEntity.ok(orderServices.deleteProductsFormOrder(orderId, productIds));
    }


    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<Product>> getProductsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderServices.getProductsByOrderId(orderId));
    }
}

