package jpashop.jpabook;

import jpashop.jpabook.domain.*;
import jpashop.jpabook.domain.item.Album;
import jpashop.jpabook.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Movie movie = new Movie();
            movie.setName("어벤져스 엔드게임");
            movie.setActor("로다쥬");
            movie.setDirector("박지성감독님");
            movie.setPrice(10000);
            movie.setStockQuantity(30);

            em.persist(movie);


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
