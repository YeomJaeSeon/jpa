package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "parent_id")
    private Long id;

    private String name;

    // parent엔티티만이 child엔티티와 관계가있을때 영속성전이를 사용하는것이좋음
    // child엔티티가 다른 엔티티(ex, member엔티티)와 같은 엔티티와 관계가있을땐, 영속성전이를 사용하지말자. 오히려복잡함
    // 1. 라이프사이클이 똑같을때
    // 2. 소유자가 하나일떄(=특정 엔티티가 개인소유할때만 사용)(이경우엔 parent엔티티만이 child엔티티만을 소유할때)
    @OneToMany(mappedBy = "parent",cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();
    // 고아객체도 영속성전이처럼 참조하는곳이 하나일때만 사용하는것이좋음. - 그렇지않으면 복합적인 문제가 발생..
    // 이유는 너가 생각해봐!

    // 양방향연관관계 편의메서드
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
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
