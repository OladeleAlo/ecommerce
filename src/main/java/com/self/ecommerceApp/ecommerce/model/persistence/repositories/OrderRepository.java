package com.self.ecommerceApp.ecommerce.model.persistence.repositories;


import com.self.ecommerceApp.ecommerce.model.persistence.User;
import com.self.ecommerceApp.ecommerce.model.persistence.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
