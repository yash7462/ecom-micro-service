package com.order.controller;

import com.order.config.ApiResponse;
import com.order.config.anotation.ValidateToken;
import com.order.dto.OrderRequest;
import com.order.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@ValidateToken
	@GetMapping(value = { "", "/" })
	public ResponseEntity<ApiResponse> getOrderList(HttpServletRequest request) {
		return orderService.getOrderList();
	}

	@ValidateToken
	@PostMapping(value = "/create")
	public ResponseEntity<ApiResponse> insertOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.insertOrder(orderRequest);
	}

	@ValidateToken
	@GetMapping(value = "/{orderNo}")
	public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable("orderNo") String orderNo) {
		return orderService.getOrderDetails(orderNo);
	}

}
