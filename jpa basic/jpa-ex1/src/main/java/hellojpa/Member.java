package hellojpa;

import hellojpa.base.BaseTable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTable {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    @OneToMany(mappedBy = "member")
    private List<MemberProduct> products = new ArrayList<>();


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Member(){ }

}
