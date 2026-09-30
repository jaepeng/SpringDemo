package com.example.mybackend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单+商品 联表查询结果
 */
@Data
public class OrderVO {

    private Long id;
    private Integer quantity;
    private BigDecimal amount;
    private LocalDateTime createTime;
    private String productName;
    private BigDecimal productPrice;
}
