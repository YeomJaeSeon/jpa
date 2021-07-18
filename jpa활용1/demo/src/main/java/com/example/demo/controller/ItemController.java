package com.example.demo.controller;

import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String itemForm(Model model){
        model.addAttribute("form", new ItemForm());

        return "items/createItemForm";
    }

    //ModelAttribute는 파라미터 요청온 파라미터 담기도하고, Model에 데이터담아서 뷰로 보내기도한다.
    @PostMapping("/items/new")
    public String createItem(@Valid @ModelAttribute("form") ItemForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/createItemForm";
        }

        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
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
        Item item = itemService.findOne(itemId);
        model.addAttribute("form", item);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable Long itemId, @Valid @ModelAttribute("form") ItemForm itemForm, BindingResult result){
        if(result.hasErrors()){
            return "items/updateItemForm";
        }

        itemService.updateItem(itemId, itemForm.getName(), itemForm.getPrice(), itemForm.getStockQuantity());

        return "redirect:/items";
    }
}
