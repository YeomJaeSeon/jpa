package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new ItemForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@ModelAttribute ItemForm form){
        Book book = Book.createBook(form.getName(), form.getPrice(),
                form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model){
        Book book = (Book)itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable Long itemId ,@ModelAttribute ItemForm itemForm){
//        Book book = new Book();
//        book.setId(itemForm.getId());
//        book.setName(itemForm.getName());
//        book.setPrice(itemForm.getPrice());
//        book.setStockQuantity(itemForm.getStockQuantity());
//        book.setAuthor(itemForm.getAuthor());
//        book.setIsbn(itemForm.getIsbn());
        //컨트롤러에서 애매하게 엔티티생성 X

        itemService.updateItem(itemId, itemForm.getName(), itemForm.getPrice(), itemForm.getStockQuantity());

        return "redirect:/items";
    }
}
