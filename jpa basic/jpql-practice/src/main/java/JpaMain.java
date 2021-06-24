import jpql.Member;
import jpql.Team;

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
            team.setName("삼성라이온즈");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            member2.setTeam(team);
            em.persist(member2);

            Integer singleResult = em.createQuery("select m.age from Member m where m.username = ?2", Integer.class)
                    .setParameter(2, "member1")
                    .getSingleResult();

            System.out.println("singleResult = " + singleResult);



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
