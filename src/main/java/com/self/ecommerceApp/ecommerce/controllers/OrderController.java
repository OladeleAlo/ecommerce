package com.self.ecommerceApp.ecommerce.controllers;


import com.self.ecommerceApp.ecommerce.model.persistence.User;
import com.self.ecommerceApp.ecommerce.model.persistence.UserOrder;
import com.self.ecommerceApp.ecommerce.model.persistence.repositories.OrderRepository;
import com.self.ecommerceApp.ecommerce.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			logger.error("Could not find user, cannot place order {}", username);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		logger.info("Order placed successfully!");
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			logger.error("Could not find orders for requested user {}", username);
			return ResponseEntity.notFound().build();
		}
		logger.info("Order history retrieved successfully!");
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
