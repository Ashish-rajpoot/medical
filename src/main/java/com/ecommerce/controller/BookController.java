//package com.ecommerce.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ecommerce.entities.Book;
//import com.ecommerce.services.BookServices;
//
//@RestController
//public class BookController {
//
//	@Autowired
//	private BookServices bookServices;
//	
//	@GetMapping("/books")
//	public List<Book> greet(){
//		List<Book> books = null;
//		books = bookServices.getBooks();
//		return books;
//	}
//	@GetMapping("/books/{id}")
//	public Book greet(@PathVariable("id") int id){
//		Book book = null;
//		book = bookServices.getBookById(id);
//		return book;
//	}
//}
