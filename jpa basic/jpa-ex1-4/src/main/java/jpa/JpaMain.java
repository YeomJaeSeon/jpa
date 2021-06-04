package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
            Member member = new Member(1L, "aaa");
            Member member1 = new Member(2L, "bbb");
            Member member2 = new Member(3L, "ccc");
            em.persist(member);
            em.persist(member1);
            em.persist(member2);

            // jpql쿼리 실행하면 자동 플러시..!(플러시 : 영속성 컨텍스트 변경사항 DB에 반영)
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            members.forEach(i -> System.out.println(i.getName()));

            System.out.println("===============");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
