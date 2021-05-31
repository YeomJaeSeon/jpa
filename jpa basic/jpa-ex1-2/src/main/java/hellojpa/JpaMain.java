package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // entity manager factory생성 - 여기서 entitymanager를 생성함
        // emf로 줄여서말하겠따. emf는 was뜰때 열고 그리고 was 내려갈때 닫으면됨.
        // 매개변수로 persistence.xml에서설정한 unitname을 넣으면된다.

        EntityManager em = emf.createEntityManager();
        //emf로 부터 em생성 - 하나의 트랜잭션에서 사용됨 트랜잭션 종료되면 em도닫자!
        // jpa에서의 데이터변경은 모두 트랜잭션 내부에서사용하자. db도 내부적으로 트랜잭션 다 사용함.

        EntityTransaction tx = em.getTransaction();
        // em으로부터 트랜잭션 얻기
        tx.begin();// 트랜잭션 시작

        try{
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class).getResultList();

            for (Member member : resultList) {
                System.out.println("member.getName() = " + member.getName());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}

