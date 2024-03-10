package com.order.service;

import com.order.config.ApiResponse;
import com.order.dto.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<ApiResponse> getOrderList();

    ResponseEntity<ApiResponse> insertOrder(OrderRequest orderRequest);

    ResponseEntity<ApiResponse> getOrderDetails(String orderNo);
}
