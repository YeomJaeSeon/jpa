package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    void 상품_등록(){
        Book book = new Book();
        book.setName("로빈슨크루소");
        book.setAuthor("염재선");

        itemService.saveItem(book);
        Item findBook = itemService.findOne(book.getId());

        assertThat(book).isEqualTo(findBook);
    }

    @Test
    void 상품_모두_조회(){
        Movie movie = new Movie();
        Book book = new Book();
        movie.setName("아름다워라");
        book.setName("잃어버린 제국을 찾아서");

        itemService.saveItem(movie);
        itemService.saveItem(book);
        List<Item> items = itemService.findItems();

        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(movie, book);
    }
}