package hellojpa.test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student {

    @Id @GeneratedValue
    @Column(name = "STUDENT_ID")
    private Long id;

    private String name;

    @Embedded
    private SubjectBook subjectBook;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_CLASSES",
    joinColumns = @JoinColumn(name = "STUDENT_ID"))
    @Column(name = "class")
    private Set<String> favoriteClasses = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookEntity> borrowedBooks = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectBook getSubjectBook() {
        return subjectBook;
    }

    public void setSubjectBook(SubjectBook subjectBook) {
        this.subjectBook = subjectBook;
    }

    public Set<String> getFavoriteClasses() {
        return favoriteClasses;
    }

    public void setFavoriteClasses(Set<String> favoriteClasses) {
        this.favoriteClasses = favoriteClasses;
    }

    public List<BookEntity> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookEntity> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
