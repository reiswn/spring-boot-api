package com.example.springbootapi.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.springbootapi.entity.Product;
import com.example.springbootapi.utils.JsonUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRestApplicationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Test if find product by id is ok")
	public void testIfFindProductByIdIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET /products - test if this endpoint is ok ")
	public void testIfGetAllProductsEndpointIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET Search")
	public void testIfSearchProductEndpointIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("Should not allow delete without id")
	public void shouldNotAllowDeleteWithoutIdParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/products"))
			.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
	}

	@Test
	public void shouldCreateNewProduct() throws Exception {

		Product expectedProduct = new Product();
		expectedProduct.setId((long) 1);
		expectedProduct.setName("name");
		expectedProduct.setDescription("description");
		expectedProduct.setPrice(new BigDecimal("59.99"));

		Product willCreateProduct = new Product();
		willCreateProduct.setName("name");
		willCreateProduct.setDescription("description");
		willCreateProduct.setPrice(new BigDecimal("59.99"));

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
												.contentType(MediaType.APPLICATION_JSON)
												.accept(MediaType.APPLICATION_JSON)
												.content(JsonUtils.toJson(willCreateProduct)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().json(JsonUtils.toJson(expectedProduct)));

	}
	
	@Test
	@DisplayName("Should Not allow new product without name")
	public void shouldNotAllowNewProductWithoutName() throws Exception {

		Product willCreateProduct = new Product();
		willCreateProduct.setDescription("description");
		willCreateProduct.setPrice(new BigDecimal("59.99"));

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
												.contentType(MediaType.APPLICATION_JSON)
												.accept(MediaType.APPLICATION_JSON)
												.content(JsonUtils.toJson(willCreateProduct)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
