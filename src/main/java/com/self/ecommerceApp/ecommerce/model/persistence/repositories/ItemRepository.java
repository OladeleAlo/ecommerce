package com.self.ecommerceApp.ecommerce.model.persistence.repositories;


import com.self.ecommerceApp.ecommerce.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);

}
