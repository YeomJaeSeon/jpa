package hellojpa.test;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class SubjectBook {

    private String author;

    private LocalDate createdDate;

    @Column(name = "BOOKNAME")
    private String name;

    public SubjectBook(){

    }
    public SubjectBook(String author, LocalDate createdDate, String name){
        this.author = author;
        this.createdDate = createdDate;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectBook that = (SubjectBook) o;
        return Objects.equals(author, that.author) && Objects.equals(createdDate, that.createdDate) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, createdDate, name);
    }
}
