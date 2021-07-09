package com.example.demo.service;

import com.example.demo.domain.item.Album;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.domain.item.Movie;
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
    void 상품_저장(){
        Book book = new Book();
        book.setName("바람과 함꼐 사라지다");

        itemService.saveItem(book);
        Item findItem = itemService.findOne(book.getId());

        assertThat(book).isEqualTo(findItem);
    }

    @Test
    void 상품_모두_조회(){
        Album album = new Album();
        album.setName("태연 2집");
        Movie movie = new Movie();
        movie.setName("악마를 보았따");
        itemService.saveItem(album);
        itemService.saveItem(movie);

        List<Item> items = itemService.findItems();

        assertThat(items).contains(album, movie);
        assertThat(items.size()).isEqualTo(2);
    }

}