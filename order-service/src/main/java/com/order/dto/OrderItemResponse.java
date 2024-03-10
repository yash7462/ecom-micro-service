package com.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    public UUID orderItemId;
    public UUID productId;
    public String productName;
    public UUID orderId;
    public Double price;
    public Double qty;

}



