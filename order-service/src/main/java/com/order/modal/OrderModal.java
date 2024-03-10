package com.order.modal;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderModal {
	@Id
	@GeneratedValue(generator = "uuid-hibernate-generator")
	@GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id")
	public UUID orderId;

	@Column(name = "order_no")
	public String orderNo;

	@Column(name = "user_id")
	public UUID userId;

	@Column(name = "total", columnDefinition = "double precision default 0")
	public Double total;

	@Column(name = "status")
	public String status;

	@Column(name = "order_date")
	public Date orderDate;

	@Column(name = "is_deleted", length = 1, columnDefinition = "int default 0")
	public int isDeleted;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	public Date createdOn;

	@UpdateTimestamp
	@Column(name = "modified_on")
	public Date modifiedOn;


}
