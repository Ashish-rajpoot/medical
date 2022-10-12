package com.ecommerce.repository;

import java.util.List;
import java.util.Set;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyOrderRepository extends JpaRepository<Order,Integer> {

	public Order findByOrderId(String orderId);
	public List<Order> findOrderByUser(String user);
	//order by orderId
	public List<Order> findOrderByOrderId(String Id);
	// order by user id
	public List<Order> findOrderByUserId(int Id);

	public List<Order> findOrderById(int Id);
}
