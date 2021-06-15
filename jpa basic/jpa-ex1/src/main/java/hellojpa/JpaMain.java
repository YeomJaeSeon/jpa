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

            Movie movie = new Movie();
            movie.setDirector("조성래");
            movie.setActor("김태희");
            movie.setName("바람과 함께 사라지다..");
            movie.setPrice(10000);
            em.persist(movie);

            Book book = new Book();
            book.setIsbn("112123-3");
            book.setName("전쟁과평화");
            book.setAuthor("히가시노 염이고");
            book.setPrice(15000);
            em.persist(book);


            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie.getName());

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
