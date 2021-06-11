package hellojpa;

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
            //등록
            Team team = new Team();
            team.setName("Manchester United");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Ronaldo");
            member.setTeam(team); // 단방향 연관관계 - 참조를 저장 -> 참조와 외래키 매핑함.
            em.persist(member);

            em.flush();
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, 1L);
            Team findTeam = findMember.getTeam();
            System.out.println(findTeam.getName());

            System.out.println("//==commit==//");
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
