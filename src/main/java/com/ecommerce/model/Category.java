package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "category_id")
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Category(String name) {
		super();
		this.name = name;
	}
	public Category() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
	
}
