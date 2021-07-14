package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ORDER_ITEM")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int orderPrice; // 개별 주문 상품가격(주문한 상품의 개별 가격)

    private int count; // 주문수량

    //== 생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    //==비즈니스로직==//
    public void cancel() {
        item.addStock(count); // 주문 취소시 주문수량만큼 상품의 재고수량 원복
    }

    //==조회로직==// - 계산할때 사용됨
    public int getToTalPrice() {
        return getOrderPrice() * count;
    }
}
