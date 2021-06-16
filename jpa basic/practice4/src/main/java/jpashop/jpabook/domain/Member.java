package jpashop.jpabook.domain;

import jpashop.jpabook.domain.base.Base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Base {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;
    private String city;
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
