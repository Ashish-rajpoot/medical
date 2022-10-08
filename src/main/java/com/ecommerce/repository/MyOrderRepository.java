package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.MyOrder;

public interface MyOrderRepository extends JpaRepository<MyOrder,Integer> {

	public MyOrder findByOrderId(String orderId);
}
