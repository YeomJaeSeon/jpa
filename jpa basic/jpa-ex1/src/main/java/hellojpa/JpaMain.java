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
            Address address = new Address("seoul", "street", "11032");
            Member member = new Member();
            member.setUsername("memberA");
            member.setHomeAddress(address);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("memberB");
            member1.setHomeAddress(address);
            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            Address address1 = new Address("incheon", address.getStreet(), address.getZipcode());
            findMember.setHomeAddress(address1);

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
