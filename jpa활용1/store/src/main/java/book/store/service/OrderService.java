package book.store.service;

import book.store.domain.*;
import book.store.repository.BookRepository;
import book.store.repository.MemberRepository;
import book.store.repository.OrderRepository;
import book.store.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long createOrder(Long memberId, Long bookId, int count){
        Member member = memberRepository.findOne(memberId);
        Book book = bookRepository.findOne(bookId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId); // persist
        order.cancel();
    }

    //find all - service
    public List<Order> findAll(OrderSearch orderSearch){
        List<Order> orders = orderRepository.findAllByCriteria(orderSearch);
        return orders;
    }
}
