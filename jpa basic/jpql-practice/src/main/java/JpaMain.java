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
//            Member member = new Member();
//            member.setUsername("호나우두");
//            member.setAge(30);
//
//            em.persist(member);
//
//            Product product = new Product();
//            product.setName("호나우두");
//            product.setPrice(2000);
//
//            em.persist(product);

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(20);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("//====clear====//");

            String jpql = "select m from Member m left join Team t on m.username = t.name";
            List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();

            System.out.println("members.size() = " + members.size());

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
