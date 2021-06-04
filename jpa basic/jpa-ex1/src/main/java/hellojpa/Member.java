package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Team")
public class Member {

    @Id // jpa에게 pk가 뭔지 알려줘야함.
    private Long id;

//    @Column(name = "username") - 컬럼의 이름도 지정해줄수있음음
   private String name;

   public Member(){

   }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
