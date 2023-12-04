package com.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
	return productRepository.findAll();	
	}
	public void  addProduct(Product product) { productRepository.save(product);}
	public void removeProductById(int id) {productRepository.deleteById(id);}
	public Optional<Product> getProductById(int id){return productRepository.findById(id);}
	public Set<Product> getAllProductByCategoryId(int id){
		return productRepository.findAllByCategory_Id(id);
	}
	public Set<Product> getProductByKeyword(String keyword){
	    return productRepository.findAllByKey(keyword);
	}

//    public Optional<Product> findByImageName(String imageName) {
//        // TODO Auto-generated method stub
//        return productRepository.findByImageName(imageName);
//    }
    
    public Product downloadImage(String imageName) {
        return productRepository.findByImageName(imageName);
    }
}
