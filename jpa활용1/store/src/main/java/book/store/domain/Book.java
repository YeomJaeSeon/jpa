package book.store.domain;

import book.store.exception.NotEnoughStockQuantityException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    @Column(name = "BOOK_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    //==비즈니스로직==//
    public void removeStock(int count) {
        int restStock = stockQuantity - count;
        if(restStock < 0){
            throw new NotEnoughStockQuantityException("재고가 부족합니다.");
        }
        stockQuantity = restStock;
    }

    public void addStock(int count){
        stockQuantity += count;
    }
}
