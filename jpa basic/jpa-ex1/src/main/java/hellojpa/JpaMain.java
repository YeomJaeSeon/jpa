package hellojpa;

import javax.persistence.*;
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

            Member member = new Member();
            member.setUsername("차두리");
            em.persist(member);


            Team team = new Team();
            team.setName("리버풀");
            // 이쪽
            team.getMembers().add(member); // 외래키변경이 일어남.
            em.persist(team);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println(findTeam.getName()); // insertable =false, updatable = false로 읽기전용
            // 주인 반대편 가짜 매핑역활을 제대로한다. @JoinColumn으로

            System.out.println("//== commit ==//");
            tx.commit();
        }catch (Exception e){
            tx.rollback();

        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }
        emf.close();
    }
}
