package book.store.service;

import book.store.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    EntityManager em;

    @Test
    void 책추가(){
        Book book = new Book();
        book.setName("바람과함꼐 사라지다");
        book.setPrice(20000);
        book.setStockQuantity(20);
        book.setAuthor("김덕자");
        book.setIsbn("1234");

        Long bookId = bookService.createBook(book);
        Book findBook = em.find(Book.class, bookId);

        assertThat(book).isEqualTo(findBook);
    }

    @Test
    void 책모두조회(){
        Book book = new Book();
        book.setName("바람과함꼐 사라지다");
        book.setPrice(20000);
        book.setStockQuantity(20);
        book.setAuthor("김덕자");
        book.setIsbn("1234");
        Book book1 = new Book();
        book1.setName("바람과함꼐 사라지다");
        book1.setPrice(20000);
        book1.setStockQuantity(20);
        book1.setAuthor("김덕자");
        book1.setIsbn("1234");
        Long bookId = bookService.createBook(book);
        Long bookId1 = bookService.createBook(book1);

        List<Book> books = bookService.findBooks();
        Book findBook = em.find(Book.class, bookId);
        Book findBook2 = em.find(Book.class, bookId1);

        assertThat(books.size()).isEqualTo(2);
        assertThat(books).contains(findBook, findBook2);
    }

    @Test
    @Rollback(value = false)
    void 책정보_수정(){
        Book book = new Book();
        book.setName("바람과함꼐 사라지다");
        book.setPrice(20000);
        book.setStockQuantity(20);
        book.setAuthor("김덕자");
        book.setIsbn("1234");
        bookService.createBook(book);
        Long bookId = book.getId();
        UpdateBookDTO bookDTO = new UpdateBookDTO();
        bookDTO.setName("바람과 ~ v2");
        bookDTO.setPrice(10000);
        bookDTO.setStockQuantity(10);

        bookService.updateBook(bookId, "바람고 함께 사라지다", 10000, 10);
        Book findBook = em.find(Book.class, bookId);

        assertThat(findBook.getName()).isEqualTo(bookDTO.getName());
        assertThat(findBook.getPrice()).isEqualTo(bookDTO.getPrice());
        assertThat(findBook.getStockQuantity()).isEqualTo(bookDTO.getStockQuantity());
    }
}