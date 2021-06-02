package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // jpa가 로딩될대 jpa를 사용하는 녀석이구나를 인식하게됨
//@Table(name="USER") // 테이블이름이 USER이면 USER에 쿼리가나감
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
