package com.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.modal.OrderModal;

@Repository
public interface OrderRepository extends JpaRepository<OrderModal, Long> {
	List<OrderModal> findByIsDeletedOrderByOrderIdDesc(int isDeleted);

	OrderModal findByOrderNoAndIsDeleted(String orderNo, int isDeleted);

}
