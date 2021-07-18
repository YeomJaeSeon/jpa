package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach(){
        Book book = new Book();
        book.setName("안녕 하니");
        book.setPrice(10000);
        book.setStockQuantity(20);
        book.setAuthor("김덕자");
        book.setIsbn("11-2");

        em.persist(book);
    }

    @Test
    void mergeTest(){
        Book book = new Book();
        book.setId(1L);
        book.setName("하니 2");

        em.merge(book);
    }

    @Test
    void dirtyCheckingTest(){
        Book findBook = em.find(Book.class, 1L);
        findBook.setName("하니 v2");
    }

}
