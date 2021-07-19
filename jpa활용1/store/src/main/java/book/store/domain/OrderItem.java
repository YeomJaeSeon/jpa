package book.store.domain;

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
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int orderPrice;
    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Book book, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        book.removeStock(count);

        return orderItem;
    }

    //==비즈니스로직==//
    public void cancel() {
        book.addStock(count);
    }

    //==조회로직==//
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
