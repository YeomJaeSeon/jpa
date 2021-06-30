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
            Team teamA = new Team();
            teamA.setName("첼시");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("맨유");
            em.persist(teamB);

            Team teamC = new Team();
            teamC.setName("토트넘");
            em.persist(teamC);

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

            Member member4= new Member();
            member4.setUsername("손흥민");
            member4.setAge(30);
            member4.setMemberType(MemberType.ADMIN);
            member4.changeTeam(teamC);
            em.persist(member4);

            int updateCount = em.createQuery("update Member m set m.age = 1")
                    .executeUpdate();
            //모든 회원의 나이 1살로 변경

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());

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
