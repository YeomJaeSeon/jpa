package book.store.repository;

import book.store.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public Long save(Book book){
        em.persist(book);
        return book.getId();
    }

    public Book findOne(Long id){
        Book book = em.find(Book.class, id);
        return book;
    }

    public List<Book> findAll(){
        List<Book>books = em.createQuery("select b from Book b", Book.class).getResultList();
        return books;
    }

    public void update(Long bookId, String name, int price, int stockQuantity){
        Book findBook = em.find(Book.class, bookId);
        findBook.setName(name);
        findBook.setPrice(price);
        findBook.setStockQuantity(stockQuantity);
    }
}
