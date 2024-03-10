package com.order.service;

import java.util.*;
import java.util.stream.Collectors;

import com.order.config.ApiResponse;
import com.order.config.OrderStatus;
import com.order.config.ServiceResponse;
import com.order.dto.OrderItemResponse;
import com.order.dto.OrderRequest;
import com.order.dto.OrderResponse;
import com.order.dto.ProductResponse;
import com.order.modal.OrderItemModal;
import com.order.repository.OrderItemRepository;
import com.order.utils.EcomConstant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.order.modal.OrderModal;
import com.order.repository.OrderRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<ApiResponse> getOrderList() {
		ApiResponse apiResponse = null;
		List<OrderModal> orderList = orderRepository.findByIsDeletedOrderByOrderIdDesc(0);
		List<OrderResponse> orderResponseList = orderList.stream().map(orderModal -> getOrder(orderModal)).collect(Collectors.toList());
		if (!orderResponseList.isEmpty()) {
			apiResponse = new ApiResponse(true, orderResponseList.size() + " Order Found", orderResponseList);
		} else {
			apiResponse = new ApiResponse(false, "0 Order Found", null);
		}
		return ResponseEntity.ok(apiResponse);
	}

	public OrderResponse getOrder(OrderModal order){
		List<OrderItemModal> orderItemModals = orderItemRepository.findByOrderId(order.getOrderId());
		List<OrderItemResponse> orderItemList = orderItemModals.stream()
				.map(oi->modelMapper.map(oi, OrderItemResponse.class)).collect(Collectors.toList());
		OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
		orderItemList.forEach(orderItemResponse -> {
			ProductResponse productResponse = this.getProductFromProductService(orderItemResponse.getProductId());
			orderItemResponse.setProductName(productResponse.getName());
		});
		orderResponse.setOrderItems(orderItemList);
		return orderResponse;
	}

	public ProductResponse getProductFromProductService(UUID productId){
		ResponseEntity<ServiceResponse> responseBody = restTemplate
				.getForEntity(EcomConstant.PRODUCT_SERVICE + productId, ServiceResponse.class);

		return modelMapper.map(responseBody.getBody().getResponse(),ProductResponse.class);
	}

	public void updateInventory(Map<UUID,Double> map){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<UUID,Double>> entity = new HttpEntity<Map<UUID,Double>>(map, headers);

		restTemplate
				.exchange(EcomConstant.PRODUCT_SERVICE+"update-inventory",HttpMethod.PUT,entity, ServiceResponse.class);

	}

	@Override
	public ResponseEntity<ApiResponse> insertOrder(@RequestBody OrderRequest orderRequest) {
		ApiResponse apiResponse = null;
		OrderModal orderModal = modelMapper.map(orderRequest,OrderModal.class);
		String orderNo = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		orderModal.setOrderNo(orderNo);
		orderModal.setStatus(OrderStatus.PENDING.toString());
		List<OrderItemModal> orderItemModals = orderRequest.getOrderItems().stream().map(o->modelMapper.map(o, OrderItemModal.class)).collect(Collectors.toList());
		OrderModal order = orderRepository.save(orderModal);
		orderItemModals.forEach(o->o.setOrderId(order.getOrderId()));
		orderItemRepository.saveAll(orderItemModals);
		if (order != null) {
			Map<UUID,Double> map = new HashMap<>();
			orderItemModals.forEach(orderItemModal -> map.put(orderItemModal.getProductId(),orderItemModal.getQty()));
			updateInventory(map);
			apiResponse = new ApiResponse(true, "Order Inserted Successfully", orderNo);
		} else {
			apiResponse = new ApiResponse(false, "Something Went Wrong on saving Order Details", null);
		}
		return ResponseEntity.ok(apiResponse);
	}

	@Override
	public ResponseEntity<ApiResponse> getOrderDetails(String orderNo) {
		ApiResponse apiResponse = null;
		OrderModal order = orderRepository.findByOrderNoAndIsDeleted(orderNo, 0);
		if (order != null) {
			OrderResponse orderResponse = getOrder(order);
			orderResponse.getOrderItems().forEach(orderItemResponse -> {
				ProductResponse productResponse = this.getProductFromProductService(orderItemResponse.getProductId());
				orderItemResponse.setProductName(productResponse.getName());
			});

			apiResponse = new ApiResponse(true, "Order Details Found", orderResponse);
		} else {
			apiResponse = new ApiResponse(false, "OOPs Order not found", null);
		}
		return ResponseEntity.ok(apiResponse);

	}

}
