package com.example.springbootapi.controller;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootapi.entity.Product;
import com.example.springbootapi.service.ProductService;

/**
 * 
 * @author willian
 * @version 1.0.0
 * 
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Product product) {
		return productService.create(product);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateProductbyId(@PathVariable Long id, @RequestBody Product product){
		return productService.update((Long) id, (Product) product);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		return productService.getById(id);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllProducts() {
		return productService.getAll();
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> searchProducts(@RequestParam(required = false) String q, 
											@RequestParam(required = false) BigDecimal min_price, 
											@RequestParam(required = false) BigDecimal max_price) {
		return productService.search(q, min_price, max_price);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
		return productService.delete(id);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ResponseMessage> handleConstraintViolationExceptions(ConstraintViolationException e) {
		
		StringBuilder message = new StringBuilder();
		e.getConstraintViolations().forEach(violation->message.append(violation.getMessage()));
		
	    return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), message.toString()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ResponseMessage> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException e) {
	    return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid JSON format."), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<ResponseMessage> handleConstraintViolationExceptions(DataIntegrityViolationException e) {
	    return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Some required field is missing."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ResponseMessage> handleAllExceptions(RuntimeException ex) {
	    return new ResponseEntity<ResponseMessage>(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Some error occurred. Please contact the system administrator."), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
