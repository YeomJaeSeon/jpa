package hellojpa;

import hellojpa.base.BaseTable;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // emf는 어플로딩시점에 딱하나만 만들어놔야함.

        EntityManager em = emf.createEntityManager();
        // db커넥션되고 쿼리날리는등 하나의 트랜잭션 은 entitymanager에 의해서 처리되어야함

        EntityTransaction tx = em.getTransaction(); // jpa는 무조건 트랜잭션 내에서 처리가 되어야함
        tx.begin(); // db트랜잭션 시작

        try{
            Child child1 = new Child();
            child1.setName("child1");
            Child child2 = new Child();
            child2.setName("child2");

            Parent parent = new Parent();
            parent.setName("parent1");
            child1.setParent(parent);
            child2.setParent(parent);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            System.out.println("==============");

            Child findChild = em.find(Child.class, child1.getId());
            System.out.println("findChild.getParent().getName() = " + findChild.getParent().getName());

            Parent findParent = em.find(Parent.class, parent.getId());
            for (Child child : findParent.getChildList()) {
                System.out.println("child : " + child);
            }
            System.out.println("==============");

            System.out.println("//==commit==//");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }
        emf.close();
    }

}
