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
            Team team = new Team();
            team.setName("첼시");
            em.persist(team);

            Member member = new Member();
            member.setUsername("램파드");
            member.setAge(20);
            member.setMemberType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("하베르츠");
            member1.setAge(60);
            member1.setMemberType(MemberType.USER);
            member1.changeTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();
            System.out.println("//====clear====//");

            Collection result = em.createQuery("select t.members from Team t", Collection.class)
                    .getResultList();

            for (Object s : result) {
                System.out.println("s = " + s);
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
