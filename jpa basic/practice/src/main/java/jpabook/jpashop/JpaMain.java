package jpabook.jpashop;

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
        // emf는 어플로딩시점에 딱하나만 만들어놔야함.

        EntityManager em = emf.createEntityManager();
        // db커넥션되고 쿼리날리는등 하나의 트랜잭션 은 entitymanager에 의해서 처리되어야함

        EntityTransaction tx = em.getTransaction(); // jpa는 무조건 트랜잭션 내에서 처리가 되어야함
        tx.begin(); // db트랜잭션 시작

        try{
            Member member = new Member();
            member.setName("염재선");
            em.persist(member);

            Order order = new Order();
            order.setMember(member);
            member.getOrders().add(order);
            em.persist(order);



            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();

        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }
        emf.close();
    }
}
