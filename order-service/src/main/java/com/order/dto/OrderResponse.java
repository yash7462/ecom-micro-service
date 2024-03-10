package com.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    public UUID orderId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date orderDate;
    public String orderNo;
    public UUID userId;
    public Double total;
    public String status;

    public List<OrderItemResponse> orderItems;
}
