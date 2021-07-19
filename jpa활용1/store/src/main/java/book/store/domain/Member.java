package book.store.domain;

import book.store.service.MemberService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
