package com.ecommerce.global;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ecommerce.model.Product;

public class GlobalData {

	public static Set<Product> cart;
	
	static {
		cart = new HashSet<Product>();
	}
}
