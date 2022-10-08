package com.ecommerce.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class MyOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	        
	private String orderId;
	
	private String amount;
	
	private String receipt;
	
	private String status;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Product product;
	
	private String paymentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}


	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public MyOrder() {
		// TODO Auto-generated constructor stub
	}
	
	public MyOrder(int id, String orderId, String amount, String receipt, String status, User user, Product product,
			String paymentId) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.amount = amount;
		this.receipt = receipt;
		this.status = status;
		this.user = user;
		this.product = product;
		this.paymentId = paymentId;
	}

	public MyOrder(String orderId, String amount, String receipt, String status, User user, Product product,
			String paymentId) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.receipt = receipt;
		this.status = status;
		this.user = user;
		this.product = product;
		this.paymentId = paymentId;
	}


}
