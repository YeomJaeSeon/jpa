package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // emf는 어플로딩시점에 딱하나만 만들어놔야함.

        EntityManager em = emf.createEntityManager();
        // db커넥션되고 쿼리날리는등 하나의 트랜잭션 은 entitymanager에 의해서 처리되어야함

        EntityTransaction tx = em.getTransaction(); // jpa는 무조건 트랜잭션 내에서 처리가 되어야함
        tx.begin(); // db트랜잭션 시작

        try{
            // em을 마치 자바 컬렉션처럼 생각( 내 겍체를 저장해주는 곳)
//            Member findMember = em.find(Member.class, 1L); // jpa를 통해서 객체를 가져오면
//            findMember.setName("HelloJPA"); // jpa가 관리되고 setter를 하면 데이터베이스의 데이터 수정이된다.

            // jpql은 객체를 대상으로 하는 객체지향 쿼리임. - 데이터베이스대상이아님. - DB방언에 맞춰서 알아서
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }

            tx.commit(); // 트랜잭션 - 커밋(안되면 롤백)
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }

        emf.close();
    }
}
