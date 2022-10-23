package com.ecommerce.global;

import java.util.ArrayList;

import com.ecommerce.model.Product;

public class GlobalData {

	public static ArrayList<Product> cart;
	
	static {
		cart = new ArrayList<Product>();
	}
}
