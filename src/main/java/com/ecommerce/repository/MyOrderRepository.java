package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.MyOrder;
import com.ecommerce.model.Product;

public interface MyOrderRepository extends JpaRepository<MyOrder,Integer> {

	public MyOrder findByOrderId(String orderId);
	public List<MyOrder> findOrderByUser(String user);
	//order by orderId
	public List<MyOrder> findOrderByOrderId(String Id);
	// order by user id
	public List<MyOrder> findOrderByUserId(int Id);

	
//	@Query("SELECT product_id FROM cartdatabase.orders_product where my_order_id=:id")
    List<Product> findAllProductsByorderId(int id);
}
