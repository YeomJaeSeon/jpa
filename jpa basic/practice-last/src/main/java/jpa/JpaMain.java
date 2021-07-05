package jpa;

import jpa.domain.Member;
import jpa.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Order order = new Order();
            order.setOrderDate(LocalDate.now());
            member.addOrder(order);
            em.persist(order);

            Order order1 = new Order();
            order1.setOrderDate(LocalDate.now());
            member.addOrder(order1);
            em.persist(order1);




            Member findMember = em.find(Member.class, member.getId());
            for (Order o : findMember.getOrders()) {
                System.out.println(o.getOrderDate());
            }


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
