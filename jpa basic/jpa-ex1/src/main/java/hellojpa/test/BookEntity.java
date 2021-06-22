package hellojpa.test;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BOOK_ENTITY")
public class BookEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @Embedded
    private SubjectBook subjectBook;

    public BookEntity(){

    }
    public BookEntity(String author, LocalDate createdBy, String name){
        subjectBook = new SubjectBook(author, createdBy, name); // 수정자 막아놓은 불변객체임. 값타입은 불변객체로 - 공유되지않도록 미연에방지함 불변객체로
        // 그래서 생성자를 통해서 멤버값 초기화할수밖에없음!
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectBook getSubjectBook() {
        return subjectBook;
    }

    public void setSubjectBook(SubjectBook subjectBook) {
        this.subjectBook = subjectBook;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
