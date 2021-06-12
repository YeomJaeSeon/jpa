package jpashop.jpabook;

import jpashop.jpabook.domain.Item;
import jpashop.jpabook.domain.Member;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
//            Member member = new Member();
//            member.setName("얌재순!");
//            em.persist(member);
//            // 영속성 컨텍스트에 들어갈때, 시퀀스오브젝트에서 id를받아서
//            // 1차캐싱되어있음
//
//            Order order = new Order();
//            order.setMember(member);
//            em.persist(order);
//
            Item item = new Item();
            item.setName("불닭볶음면");
            item.setPrice(1000);
            item.setStockQuantity(10);
            em.persist(item);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setItem(item);
//            orderItem.setOrder(order);
//            orderItem.setCount(1);
//            orderItem.setOrderPrice(1000);
//            em.persist(orderItem);

            System.out.println("=====");
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
