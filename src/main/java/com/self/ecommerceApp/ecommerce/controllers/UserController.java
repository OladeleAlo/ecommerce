package com.self.ecommerceApp.ecommerce.controllers;


import com.self.ecommerceApp.ecommerce.model.persistence.Cart;
import com.self.ecommerceApp.ecommerce.model.persistence.User;
import com.self.ecommerceApp.ecommerce.model.persistence.repositories.CartRepository;
import com.self.ecommerceApp.ecommerce.model.persistence.repositories.UserRepository;
import com.self.ecommerceApp.ecommerce.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		if (createUserRequest.getPassword().length() < 7 ||
			!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())
		) {
			System.out.println(" " +
					createUserRequest.getUsername());
            logger.error("Either length is less than 7 or password and conf-password do not match. Unable to create {}",
                    createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		try {
			userRepository.save(user);
			logger.info("User created with name: {}", user.getUsername());
		} catch (DataIntegrityViolationException ex) {
			logger.error("User not created with exception: {}", user.getUsername());
		}
		return ResponseEntity.ok(user);
	}
	
}
