package com.example.springbootapi.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springbootapi.entity.Product;

/**
 * 
 * @author willian
 *
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("SELECT p FROM Product p WHERE "
			+ "(?1 is NULL OR LOWER(CONCAT(p.name, p.description)) LIKE %?1%) "
			+ "AND (?2 is NULL OR p.price >= ?2) "
			+ "AND (?3 is NULL OR p.price <= ?3) ")
    List<Product> findAllByPriceNameDescription(@Param("q") String q, @Param("min_price") BigDecimal min_price, @Param("max_price") BigDecimal max_price);
	
}
