package com.example.mybackend.controller;

import com.example.mybackend.common.Result;
import com.example.mybackend.dto.OrderVO;
import com.example.mybackend.entity.Order;
import com.example.mybackend.entity.Product;
import com.example.mybackend.entity.User;
import com.example.mybackend.mapper.OrderMapper;
import com.example.mybackend.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "订单", description = "订单查询")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Operation(summary = "查我的订单（关联商品信息）")
    @GetMapping("/my")
    public Result<List<OrderVO>> myOrders(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        List<OrderVO> list = orderMapper.findUserOrders(user.getId());
        return Result.success(list);
    }

    @Operation(summary = "创建订单")
    @PostMapping
    public Result<Order> createOrder(@RequestParam Long productId,
                                     @RequestParam Integer quantity,
                                     HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return Result.error(404, "商品不存在");
        }
        Order order = new Order();
        order.setUserId(user.getId());
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        orderMapper.insert(order);
        return Result.success(order);
    }
}
