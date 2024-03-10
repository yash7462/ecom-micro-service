package com.order.repository;

import com.order.modal.OrderItemModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModal, Long> {
	List<OrderItemModal> findByOrderId(UUID orderId);
}
