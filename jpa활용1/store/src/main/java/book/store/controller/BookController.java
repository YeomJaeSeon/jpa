package book.store.controller;

import book.store.domain.Book;
import book.store.repository.BookRepository;
import book.store.service.BookService;
import book.store.service.UpdateBookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping("/items/new")
    public String createBookForm(Model model){
        model.addAttribute("form", new BookForm());

        return "book/createBookForm";
    }

    @PostMapping("/items/new")
    public String createBook(@ModelAttribute BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        bookService.createBook(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Book> books = bookService.findBooks();
        model.addAttribute("items", books);
        return "book/bookList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, Model model){
        Book book = bookRepository.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        model.addAttribute("form", form);

        return "book/updateBookForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable Long itemId, @ModelAttribute BookForm form){
        bookService.updateBook(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
