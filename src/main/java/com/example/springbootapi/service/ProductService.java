package com.example.springbootapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.springbootapi.entity.Product;
import com.example.springbootapi.repository.ProductRepository;

/**
 * 
 * @author willian
 *
 */

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<?> create(Product product) {
		
		Product productCreated = productRepository.save(product);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
	}
	
	public ResponseEntity<?> update(Long id, Product product) {
		
		Product existingProduct = productRepository.findById(id).orElse(null);
		
		if(existingProduct == null) {
			return ResponseEntity.notFound().build();
		}
		
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setPrice(product.getPrice());
		
		existingProduct = productRepository.save(existingProduct);
		
		return ResponseEntity.status(HttpStatus.OK).body(existingProduct);
	}

	public ResponseEntity<?> getById(Long id) {
		
		Product product = productRepository.findById(id).orElse(null);
		
		if(product == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	public ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}

	public ResponseEntity<?> search(String q, BigDecimal min_price, BigDecimal max_price){
		
		/*
		 * Extra: If all params are null, return blank list, otherwise, requester can use this like a getAll route.
		 */
		if(q == null && min_price==null && max_price==null) return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<Product>());
		
		/*
		 * Extra: For best results this parameter and the same in the database will be compared in lower case.
		 */
		q = q.toLowerCase();
		
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAllByPriceNameDescription(q, min_price, max_price)); 
	}
	
	public ResponseEntity<?> delete(Long id) {
		
		Product product = productRepository.findById(id).orElse(null);

		if (product == null) {
			return ResponseEntity.notFound().build();
		} 
		
		productRepository.delete(product);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
