package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // jpa에서 데이터변경은 트랜잭션 내에서 이루어져야한다.
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId); //엔티티의 데이터를 직접변경하기위해 뽑음 (em.find)로
        //변경은 set, set쓰지말고 의미있는 메서드를 만들자 - Item 엔티티 내부에만들면될듯
//        findItem.setName(param.getName());
//        findItem.setPrice(param.getPrice());
//        findItem.setStockQuantity(param.getStockQuantity());
        findItem.updateItem(name, price, stockQuantity); // setter대신 의미있는 메서드로.. 이 메서드(행동)은 객체 내부에 상태와함께있음
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
