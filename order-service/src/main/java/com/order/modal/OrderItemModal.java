package com.order.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItemModal {
	@Id
	@GeneratedValue(generator = "uuid-hibernate-generator")
	@GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_item_id")
	public UUID orderItemId;

	@Column(name = "product_id")
	public UUID productId;

	@Column(name = "order_id")
	public UUID orderId;

	@Column(name = "price", columnDefinition = "double precision default 0")
	public Double price;

	@Column(name = "qty", columnDefinition = "double precision default 0")
	public Double qty;

	@Column(name = "is_deleted", length = 1, columnDefinition = "int default 0")
	public int isDeleted;

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	public Date createdOn;

	@UpdateTimestamp
	@Column(name = "modified_on")
	public Date modifiedOn;

}
