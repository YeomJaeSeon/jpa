package book.store.service;

import book.store.domain.Book;
import book.store.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long createBook(Book book){
        Long bookId = bookRepository.save(book);
        return bookId;
    }

    public List<Book> findBooks(){
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Transactional
    public void updateBook(Long bookId, String name, int price, int stockQuantity){
        bookRepository.update(bookId, name, price, stockQuantity);
    }
}
