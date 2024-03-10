package com.product.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.product.config.ApiResponse;
import com.product.dto.ProductRequest;
import com.product.dto.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.modal.ProductModal;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;


	public ProductModal insertProduct(ProductModal productVo) {
		return productRepository.saveAndFlush(productVo);
	}

	@Override
	public List<ProductModal> findByIsDeletedOrderByProductIdDesc(int isDeleted) {
		return productRepository.findByIsDeletedOrderByProductIdDesc(isDeleted);
	}

	@Override
	public ProductModal findByProductIdAndIsDeletedOrderByProductIdDesc(UUID productId, int isDeleted) {
		return productRepository.findByProductIdAndIsDeletedOrderByProductIdDesc(productId, isDeleted);
	}

	@Override
	public ResponseEntity<ApiResponse> getAllProduct() {
		ApiResponse apiResponse = null;
		List<ProductModal> productList = this.findByIsDeletedOrderByProductIdDesc(0);
		if (!productList.isEmpty()) {
			List<ProductResponse> productResponseList = productList.stream().map(p->modelMapper.map(p, ProductResponse.class)).collect(Collectors.toList());
			apiResponse = new ApiResponse(true, productResponseList.size() + " Products Found", productResponseList);
		} else {
			apiResponse = new ApiResponse(false, "0 Products Found", null);
		}
		return ResponseEntity.ok(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponse> insertProduct(ProductRequest productRequest) {
		ApiResponse apiResponse = null;
		String message = "Create";
		if(productRequest.getProductId()!=null){
			message = "Update";
		}
		ProductModal product = modelMapper.map(productRequest, ProductModal.class);
		product = this.insertProduct(product);
		if (product != null && product.getProductId()!=null) {
			apiResponse = new ApiResponse(true, "Product "+message+" Successfully", modelMapper.map(product,ProductResponse.class));
		} else {
			apiResponse = new ApiResponse(false, "Something Went Wrong on "+message+" Product Details", null);
		}
		return ResponseEntity.ok(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponse> getProductDetails(UUID productId){
		ApiResponse apiResponse = null;
		ProductModal product = this.findByProductIdAndIsDeletedOrderByProductIdDesc(productId, 0);
		if (product != null) {
			apiResponse = new ApiResponse(true, "Product Details Found", modelMapper.map(product,ProductResponse.class));
		} else {
			apiResponse = new ApiResponse(false, "OOPs Product not found", null);
		}
		return ResponseEntity.ok(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponse> deleteProductDetails(UUID productId) {
		productRepository.deleteProduct(productId);
		return ResponseEntity.ok(new ApiResponse(true, "Product Deleted",null));
	}

	@Override
	public ResponseEntity<ApiResponse> updateProductInventory(UUID productId, Double inventory) {
		productRepository.updateProductInventory(productId,inventory);
		return ResponseEntity.ok(new ApiResponse(true, "Inventory Updated",null));
	}

	@Override
	public ResponseEntity<ApiResponse> updateInventory(Map<UUID, Double> map) {
		map.entrySet().forEach(m->{
			productRepository.updateProductInventory(m.getKey(),m.getValue());
		});
		return ResponseEntity.ok(new ApiResponse(true, "Inventory Updated",null));
	}
}
