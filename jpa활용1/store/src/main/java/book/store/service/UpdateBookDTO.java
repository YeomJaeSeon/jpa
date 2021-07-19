package book.store.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateBookDTO {
    private String name;
    private int price;
    private int stockQuantity;
}
