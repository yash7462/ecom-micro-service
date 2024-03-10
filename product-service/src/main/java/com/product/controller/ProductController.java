package com.product.controller;

import com.product.config.anotation.ValidateToken;
import com.product.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.product.config.ApiResponse;
import com.product.modal.ProductModal;
import com.product.service.ProductService;

import lombok.extern.java.Log;

import java.util.Map;
import java.util.UUID;

@Log
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
    ProductService productService;

	@ValidateToken
	@GetMapping(value = { "", "/" })
	public ResponseEntity<ApiResponse> getAllProduct() {
		return productService.getAllProduct();
	}

	@ValidateToken
	@PostMapping(value = "/create")
	public ResponseEntity<ApiResponse> insertProduct(@RequestBody ProductRequest productRequest) {
		return productService.insertProduct(productRequest);
	}

	@ValidateToken
	@GetMapping(value = "/{productId}")
	public ResponseEntity<ApiResponse> getProductDetails(@PathVariable("productId") UUID productId) {
		return productService.getProductDetails(productId);
	}

	@ValidateToken
	@DeleteMapping(value = "/{productId}")
	public ResponseEntity<ApiResponse> deleteProductDetails(@PathVariable("productId") UUID productId) {
		return productService.deleteProductDetails(productId);
	}

	@ValidateToken
	@PutMapping(value = "/inventory/{productId}")
	public ResponseEntity<ApiResponse> updateProductInventory(@PathVariable("productId") UUID productId,
															@RequestParam(name = "inventory",required = true,defaultValue = "0") Double inventory) {
		return productService.updateProductInventory(productId,inventory);
	}

	@ValidateToken
	@PutMapping(value = "/update-inventory")
	public ResponseEntity<ApiResponse> updateInventory(@RequestBody Map<UUID,Double> map) {
		return productService.updateInventory(map);
	}
}
