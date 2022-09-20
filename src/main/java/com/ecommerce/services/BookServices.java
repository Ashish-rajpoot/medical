package com.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.Book;

@Component
public class BookServices {

	
	public static List<Book> books = new ArrayList<>();
	
	static {
		books.add(new Book( 2,"book1",123));
		books.add(new Book( 3,"book2",1123));
		books.add(new Book( 4,"book3",1223));
	}
	
	public List<Book> getBooks(){
		return books;
	}
	
	public Book getBookById(int id) {
		Book book = null;
		book=books.stream().filter(e->e.getId()==id).findFirst().get();
		return book;
	}
}
