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



            em.flush();
            em.clear();



            // MemberDTO 생성자호출.
            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : resultList) {
                System.out.println("memberDTO = " + memberDTO.getUsername());
                System.out.println("memberDTO = " + memberDTO.getAge());
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
