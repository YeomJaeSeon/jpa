package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderRepository orderRepository;

    @GetMapping("/order")
    public String order(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/order";
    }

//    @PostMapping("/order")
//    public String createOrderV1(@ModelAttribute OrderDTO orderDTO){
//        orderService.order(orderDTO.getMemberId(), orderDTO.getItemId(), orderDTO.getCount());
//
//        return "redirect:/orders";
//    }

    @PostMapping("/order")
    public String createOrderV2(@RequestParam Long memberId, @RequestParam Long itemId, @RequestParam int count){
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String list(@ModelAttribute OrderSearch orderSearch ,Model model){
        List<Order> orders = orderRepository.findAll(orderSearch);

        model.addAttribute("orders", orders);
//        model.addAttribute("orderSearch", orderSearch); - 생략가능 modelAttribute가 model에 데이터담는 역할도한다.

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
