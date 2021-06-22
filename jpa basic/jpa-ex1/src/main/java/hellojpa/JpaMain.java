package hellojpa;

import hellojpa.base.BaseTable;
import hellojpa.test.BookEntity;
import hellojpa.test.Student;
import hellojpa.test.SubjectBook;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
            Student student = new Student();
            student.setName("염재선");

            student.getFavoriteClasses().add("체육");
            student.getFavoriteClasses().add("음악");

            BookEntity bookEntity = new BookEntity("이순신", LocalDate.now(), "징비록");
            BookEntity bookEntity1 = new BookEntity("파브르", LocalDate.now(), "파브르 곤충기");
            BookEntity bookEntity2 = new BookEntity("쉬운 DB설계", LocalDate.now(), "안드레아");

            student.getBorrowedBooks().add(bookEntity);
            student.getBorrowedBooks().add(bookEntity1);
            student.getBorrowedBooks().add(bookEntity2);
            bookEntity.setStudent(student);
            bookEntity1.setStudent(student);
            bookEntity2.setStudent(student);

            student.setSubjectBook(new SubjectBook("물리가좋아", LocalDate.now(), "박막례"));

            em.persist(student);

            em.flush();
            em.clear();

            System.out.println("==========START==========");
            Student findStudent = em.find(Student.class, student.getId());

            findStudent.getBorrowedBooks().remove(0);
            


            System.out.println("//==commit==//");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close(); // entitymanager 사용다하면 꼭 닫아줘야함.
        }
        emf.close();
    }

}
