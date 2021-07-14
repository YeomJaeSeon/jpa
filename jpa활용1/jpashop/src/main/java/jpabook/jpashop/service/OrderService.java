package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); // order만 persist해도 Order 엔티티의
        // 연관관계들에대한 Cascade옵션으로 Order persist하면 delivery와 orderItem모두 persist된다.
        // 이런 영속성에대한 옵션은 자동으로 영속화하게하려는 child 엔티티를 개인소유하고있을때 유용하다.
        // parent 엔티티가 child 엔티티를 개인 소유하고있을 경우에! - life cycle이 복잡하면 영속화가 이상하게된다.
        // OrderItem, Delivery를 Order엔티티가 개인소유하고있다. 이럴경우 Cascade를 사용한다.(즉 persist하는 lifecycle이 같을 경우만쓴다!)


        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel(); // 이문장을 까보면 jpa의 장점을알수있다. dirty checking에의한 자동 sql 나가는 장점! jpa사용안하면 업데이트되는 필드에대해 전부 쿼리를날려야함
    }


    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll()
//    }
}
