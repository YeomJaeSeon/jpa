package hellojpa;

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
            Team team = new Team();
            team.setName("삼성라이온즈");
            em.persist(team);

            Member member = new Member();
            member.setName("박지성");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Long memberId = member.getId();
            Member findMember = em.find(Member.class, memberId);
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m : " + m.getName());
            }


            System.out.println("=============");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
