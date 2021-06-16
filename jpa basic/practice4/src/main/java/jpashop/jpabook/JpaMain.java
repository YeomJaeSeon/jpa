package jpashop.jpabook;

import jpashop.jpabook.domain.*;
import jpashop.jpabook.domain.item.Album;
import jpashop.jpabook.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Album album = new Album();
            album.setName("BTS 정규 1집");
            album.setArtist("방배동 살쾡이");
            album.setEtc("60억 포켓몬스터");
            album.setPrice(2000);
            album.setStockQuantity(1000);
            album.setCreatedDateTime(LocalDateTime.now());
            album.setModifiedDateTime(LocalDateTime.now());
            em.persist(album);

            Movie movie = new Movie();
            movie.setName("명량");
            movie.setActor("최민식");
            movie.setDirector("박점례");
            movie.setStockQuantity(2000);
            movie.setPrice(1000);
            movie.setCreatedDateTime(LocalDateTime.now());
            movie.setModifiedDateTime(LocalDateTime.now());
            em.persist(movie);

            Movie movie2 = new Movie();
            movie2.setName("명량");
            movie2.setActor("최민식");
            movie2.setDirector("박점례");
            movie2.setStockQuantity(2000);
            movie2.setPrice(1000);
            movie2.setCreatedDateTime(LocalDateTime.now());
            movie2.setModifiedDateTime(LocalDateTime.now());
            em.persist(movie2);

            System.out.println("============");
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
