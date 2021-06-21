package hello.jpa;

import hello.jpa.domain.Delivery;
import hello.jpa.domain.Order;

import javax.persistence.*;
import java.time.LocalDate;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Order order = new Order();
            order.setOrderDate(LocalDate.now());

            Delivery delivery = new Delivery();
            delivery.setCity("seoul");
            order.setDelivery(delivery);
            delivery.setOrder(order); //연관관계 주인아님 양방향 참조 세팅만해주는거임 순수자바테스트를위해서.
            em.persist(delivery);

            em.flush();
            em.clear();

            Delivery findDelivery = em.find(Delivery.class, delivery.getId());

            System.out.println("//==commit==//");
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
