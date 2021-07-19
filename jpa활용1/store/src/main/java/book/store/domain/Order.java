package book.store.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // ORDER, CANCEL

    //==연관관계 편의메서드==//
    public void addMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem ...orderItems){
        Order order = new Order();
        order.addMember(member);
        order.addDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    //==비즈니스로직==//
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송이 완료된 상품은 주문 취소가 불가능합니다.");
        }

        setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회로직==//
    public int getTotalPrice(){
        int sum = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        return sum;
    }
}
