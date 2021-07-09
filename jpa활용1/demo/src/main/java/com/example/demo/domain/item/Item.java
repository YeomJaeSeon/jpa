package com.example.demo.domain.item;

import com.example.demo.domain.Category;
import com.example.demo.exception.NotEnoughStockQuantity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //== 도메인 내부의 stockQuantity만의 비즈니스로직은 같은 클래스 내부에있는게 응집도가 높다==//
    // 다른 필드나 메서드에 영향을 받지않고 단순히 Item클래스 내부의 필드만 수정하는 메서드 setter와 비슷하다!!

    //==비즈니스 로직==//
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restQuantity = this.stockQuantity - quantity;
        if(restQuantity < 0){
            throw new NotEnoughStockQuantity("재고가 충분하지 않습니다");
        }else{
            this.stockQuantity = restQuantity;
        }
    }
}
