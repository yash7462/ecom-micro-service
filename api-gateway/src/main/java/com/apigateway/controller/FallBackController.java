package com.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

	@GetMapping("product-service-down")
	public String contactServiceDown() {
		return "Currently Product Service Is Down!";
	}

	@GetMapping("order-service-down")
	public String salesServiceDown() {
		return "Currently Order Service Is Down!";
	}

	@GetMapping("auth-service-down")
	public String authServiceDown() {
		return "Currently Auth Service Is Down!";
	}
}
