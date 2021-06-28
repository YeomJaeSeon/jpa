import jpql.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team teamA = new Team();
            teamA.setName("첼시");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("맨유");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("램파드");
            member1.setAge(20);
            member1.setMemberType(MemberType.ADMIN);
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("하베르츠");
            member2.setAge(60);
            member2.setMemberType(MemberType.USER);
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("호날두");
            member3.setAge(50);
            member3.setMemberType(MemberType.USER);
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();
            System.out.println("//====clear====//");

            String query = "select t from Team t join t.members";

            List<Team> result = em.createQuery(query, Team.class)
                    .getResultList();

            for (Team o : result) {
                System.out.println("o = " + o.getName() + ", team : " + o.getMembers().size());
                for (Member member : o.getMembers()) {
                    System.out.println("member.getUsername() = " + member.getUsername());
                }
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
