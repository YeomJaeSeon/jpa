package com.example.demo.service;

import com.example.demo.domain.Address;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.exception.NotEnoughStockQuantity;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 주문생성(){
        //given
        Member member = createMember();
        Item item = createItem("객체지향의 사실과 오해", 10000, 10);
        int orderCount = 3;



        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order resultOrder = orderRepository.findOne(orderId);

        assertThat(item.getStockQuantity()).isEqualTo(7);
        assertThat(resultOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(resultOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(resultOrder.getTotalPrice()).isEqualTo(orderCount * 10000);

    }

    @Test
    void 주문취소(){
        //given
        Member member = createMember();
        Item item = createItem("객체지향의 사실과 오해", 10000, 10);
        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        Order resultOrder = orderRepository.findOne(orderId);

        //then
        assertThat(resultOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 주문생성시_재고부족(){
        //given
        Member member = createMember();
        Item item = createItem("객체지향의 사실과 오해", 10000, 10);

        //when
        int orderCount = 30;

        //then
        Assertions.assertThrows(NotEnoughStockQuantity.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        itemRepository.save(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("염재선");
        member.setAddress(new Address("한국", "선운지구", "123-13"));
        memberRepository.save(member);
        return member;
    }

}