import jpql.*;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("첼시");
            em.persist(team);

            Team team1 = new Team();
            team1.setName("맨유");
            em.persist(team1);

            Member member = new Member();
            member.setUsername("램파드");
            member.setAge(20);
            member.changeTeam(team);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("하베르츠");
            member1.setAge(21);
            member1.changeTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("박지성");
            member2.setAge(22);
            member2.changeTeam(team1);
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("//====clear====//");

            String jpql = "select m from Member m where m.age > ANY(select m2.age from Member m2 where m.username <> m2.username)";
            List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();

            System.out.println("members.size() = " + members.size());
            for (Member m : members) {
                System.out.println(m);
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
