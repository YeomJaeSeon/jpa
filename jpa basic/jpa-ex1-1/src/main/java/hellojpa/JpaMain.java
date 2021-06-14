package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;

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
            member.setName("기성용");
//            member.setTeam(team);
//            team.getMembers().add(member);
            team.addMember(member);
            em.persist(member);

            // 데이터중심 도메인객체 설계의 문제점
            //테이블 중심.. (DB중심)
//            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
//            System.out.println(findTeam.getName());

            // 참조와 외래키 매핑
//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//            System.out.println(findTeam.getName());


            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getName());
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
