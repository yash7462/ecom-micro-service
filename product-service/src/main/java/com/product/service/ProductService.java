package com.product.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.product.config.ApiResponse;
import com.product.dto.ProductRequest;
import com.product.modal.ProductModal;
import org.springframework.http.ResponseEntity;

public interface ProductService {
	public List<ProductModal> findByIsDeletedOrderByProductIdDesc(int isDeleted);

	public ProductModal findByProductIdAndIsDeletedOrderByProductIdDesc(UUID productId, int isDeleted);

	ResponseEntity<ApiResponse> getAllProduct();

	ResponseEntity<ApiResponse> insertProduct(ProductRequest productRequest);

	ResponseEntity<ApiResponse> getProductDetails(UUID productId);

	ResponseEntity<ApiResponse> deleteProductDetails(UUID productId);

	ResponseEntity<ApiResponse> updateProductInventory(UUID productId, Double inventory);

    ResponseEntity<ApiResponse> updateInventory(Map<UUID, Double> map);
}
