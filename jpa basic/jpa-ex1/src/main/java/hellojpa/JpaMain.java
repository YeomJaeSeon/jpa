package hellojpa;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // emf는 어플로딩시점에 딱하나만 만들어놔야함.

        EntityManager em = emf.createEntityManager();
        // db커넥션되고 쿼리날리는등 하나의 트랜잭션 은 entitymanager에 의해서 처리되어야함

        EntityTransaction tx = em.getTransaction(); // jpa는 무조건 트랜잭션 내에서 처리가 되어야함
        tx.begin(); // db트랜잭션 시작

        try{
            Locker locker = new Locker();
            locker.setName("locker 1");
            em.persist(locker);

            Locker locker1 = new Locker();
            locker1.setName("locker 2");
            em.persist(locker1);

            Team team = new Team();
            team.setName("해태 타이거즈");
            em.persist(team);

            Member member = new Member();
            member.setUsername("박지성");
            member.setTeam(team);
            member.setLocker(locker);//연관관계 주인에 값넣음
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("이영표");
            member1.setTeam(team);
            member1.setLocker(locker1);
            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("locker name : " + findMember.getLocker().getName());
            Locker findLocker = em.find(Locker.class, locker.getId());
            System.out.println("member name : " + findLocker.getMember().getUsername());
            Locker findLocker2 = em.find(Locker.class, locker1.getId());
            System.out.println("member1 name : " + findLocker2.getMember().getUsername());


            System.out.println("//== commit ==//");
            tx.commit();
        }catch (Exception e){
            tx.rollback();

        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }
        emf.close();
    }
}
