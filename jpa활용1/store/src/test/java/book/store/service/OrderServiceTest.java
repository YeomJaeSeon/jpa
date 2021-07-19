package book.store.service;

import book.store.domain.*;
import book.store.exception.NotEnoughStockQuantityException;
import book.store.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;

    @Test
    void 주문생성(){
        Member member = createMember();
        Book book = createBook("불멸의 이순신", 20000, 20, "김덕자", "123");
        int orderCount = 6;

        Long orderId = orderService.createOrder(member.getId(), book.getId(), orderCount);
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(book.getStockQuantity()).isEqualTo(14);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(book.getPrice() * orderCount);

    }

    @Test
    void 주문취소(){
        Member member = createMember();
        Book book = createBook("안녕", 20000, 20, "김덕자", "213");
        int orderCount = 4;
        Long orderId = orderService.createOrder(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(20);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    void 재고초과시_NotEnough예외발생(){
        Member member = createMember();
        Book book = createBook("안녕", 20000, 2, "김덕자", "213");

        int orderCount = 4;

        Assertions.assertThrows(NotEnoughStockQuantityException.class, () -> orderService.createOrder(member.getId(), book.getId(), orderCount));
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("광주", "고고고", "123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        em.persist(book);

        return book;
    }
}