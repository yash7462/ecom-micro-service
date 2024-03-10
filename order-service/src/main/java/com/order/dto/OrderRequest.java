package com.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    public UUID userId;
    public Double total;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date orderDate;
    public List<OrderItemRequest> orderItems;
}
