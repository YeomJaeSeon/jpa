package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "ORDERITEM_SEQ_GENERATOR",
        sequenceName = "ORDERITEM_SEQ",
        initialValue = 1
)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "ORDERITEM_SEQ_GENERATOR")
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    // 단방향 매핑부터 설정해라 - 주인관계에서 참조와 외래키 매핑부터해라.
    // 양방향은 개발할때 필요하면 사용.
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
