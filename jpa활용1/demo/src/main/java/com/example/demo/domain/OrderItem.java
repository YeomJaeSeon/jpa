package com.example.demo.domain;

import com.example.demo.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int orderPrice; // 주문한 상품의 개별 가격

    private int count; // 주문 상품 개수

    //==생성 메서드==// - 정적 팩토리 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 상품 제고 줄어듬

        return orderItem;
    }

    //== 비즈니스 로직==//
    public void cancel() {
        item.addStock(count); // 주문취소했으니 상품 재고 증가
    }

    //==조회로직==//
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
