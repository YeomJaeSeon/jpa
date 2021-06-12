package jpabook.jpashop;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
            Member member = new Member();
            member.setName("염재선");
            em.persist(member);

            Order order = new Order();
            member.addOrder(order);
            em.persist(order);

            Member findMember = em.find(Member.class, member.getId());
            for (Order o : findMember.getOrders()) {
                System.out.println(o.getMember().getName());
            }


            System.out.println("=========");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
