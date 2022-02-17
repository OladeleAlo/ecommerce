package com.self.ecommerceApp.ecommerce.model.persistence.repositories;


import com.self.ecommerceApp.ecommerce.model.persistence.Cart;
import com.self.ecommerceApp.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
