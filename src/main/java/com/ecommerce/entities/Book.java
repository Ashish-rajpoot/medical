//package com.ecommerce.entities;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Book {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int id;
//	@Column(name = "name")
//	private String nameString;
//	@Column(name = "price")
//	private int price;
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getNameString() {
//		return nameString;
//	}
//	public void setNameString(String nameString) {
//		this.nameString = nameString;
//	}
//	public int getPrice() {
//		return price;
//	}
//	public void setPrice(int price) {
//		this.price = price;
//	}
//	@Override
//	public String toString() {
//		return "Book [id=" + id + ", nameString=" + nameString + ", price=" + price + "]";
//	}
//	public Book(int id, String nameString, int price) {
//		super();
//		this.id = id;
//		this.nameString = nameString;
//		this.price = price;
//	}
//	public Book(String nameString, int price) {
//		super();
//		this.nameString = nameString;
//		this.price = price;
//	}
//	
//	
//	
//	
//	
//}
