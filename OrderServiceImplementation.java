package com.example.demo.services.implementations;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.OrderServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderServices{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Validate Customer
        Customer customer = customerRepository.findById(orderDTO.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Create Order Object
        Order order = new Order();
        order.setOrderNum(UUID.randomUUID().toString()); // Generate unique order number
        order.setStatus(OrderStatus.INITIATED); // Default status
        order.setCustomer(customer);

        // Add Products to Order (if provided)
        if (orderDTO.getProducts() != null && !orderDTO.getProducts().isEmpty()) {
            List<Product> products = productRepository.findAllById(
                    orderDTO.getProducts().stream()
                            .map(ProductDTO::getId)  // Assuming orderDTO contains a list of ProductDTO
                            .collect(Collectors.toList())
            );
            if (products.size() != orderDTO.getProducts().size()) {
                throw new IllegalArgumentException("Some products not found");
            }
            order.setProducts(products);
        }


        // Save Order to Database
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }



    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return modelMapper.map(order, OrderDTO.class);
    }


    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderDTO.getId()));

        if (orderDTO.getStatus() != null) {
            existingOrder.setStatus(orderDTO.getStatus());
        }

        if (orderDTO.getProducts() != null && !orderDTO.getProducts().isEmpty()) {
            List<Product> products = productRepository.findAllById(
                    orderDTO.getProducts().stream().map(ProductDTO::getId).collect(Collectors.toList())
            );

            if (products.size() != orderDTO.getProducts().size()) {
                throw new IllegalArgumentException("Some products not found");
            }

            existingOrder.setProducts(products);
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order")
        );

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO addProductsToOrder(Long orderId, List<Product> products) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order")
        );

        List<Product> existingProducts = order.getProducts();
        existingProducts.addAll(products);
        order.setProducts(existingProducts);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO deleteProductsFormOrder(Long orderId, List<Long> productIds) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order")
        );

        List<Product> existingProducts = order.getProducts();
        existingProducts.removeIf(product -> productIds.contains(product.getId()));
        order.setProducts(existingProducts);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


    @Override
    public Page<OrderDTO> getAllOrder(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> modelMapper.map(order, OrderDTO.class));
    }


    @Override
    public List<OrderDTO> getAllOrderByStatus(OrderStatus orderStatus) {
        // Step 1: Fetch paginated orders by status
        List<Order> ordersPage = orderRepository.findByStatus(orderStatus);

        // Step 2: Convert List<Order> to List<OrderDTO>
        return ordersPage.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrderByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find customer")
        );
        List<Order> orders = customer.getOrders();

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order")
        );
        return order.getProducts();
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order")
        );
        orderRepository.delete(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find order with ID: " + orderId));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel an order that has already been delivered.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

}
