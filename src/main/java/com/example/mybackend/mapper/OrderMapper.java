package com.example.mybackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybackend.entity.Order;
import com.example.mybackend.dto.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 多表关联查询：查某用户的订单，同时带出商品名和商品价格
     */
    @Select("""
            SELECT o.id, o.quantity, o.amount, o.create_time,
                   p.name AS product_name, p.price AS product_price
            FROM orders o
            LEFT JOIN product p ON o.product_id = p.id
            WHERE o.user_id = #{userId}
            ORDER BY o.create_time DESC
            """)
    List<OrderVO> findUserOrders(Long userId);
}
