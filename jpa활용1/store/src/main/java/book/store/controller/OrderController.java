package book.store.controller;

import book.store.domain.Book;
import book.store.domain.Member;
import book.store.domain.Order;
import book.store.repository.OrderSearch;
import book.store.service.BookService;
import book.store.service.MemberService;
import book.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String orderForm(Model model){
        List<Member> members = memberService.findAllMembers();
        List<Book> books = bookService.findBooks();
        model.addAttribute("members", members);
        model.addAttribute("items", books);

        return "order/ordersForm";
    }

    @PostMapping("/order")
    public String createOrder(@RequestParam Long memberId, @RequestParam Long itemId, @RequestParam int count){
        Long orderId = orderService.createOrder(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findAll(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
