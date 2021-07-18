package com.example.demo.repository;

import com.example.demo.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName; // 회원이름
    private OrderStatus orderStatus; // 주문상태[ORDER, CANCEL]

    @Override
    public String toString() {
        return "OrderSearch{" +
                "memberName='" + memberName + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
