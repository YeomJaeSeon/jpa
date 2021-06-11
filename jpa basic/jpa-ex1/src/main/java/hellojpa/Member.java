package hellojpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQUENCE_NAME",
        sequenceName = "MEMBER_SEQ"
)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "MEMBER_SEQUENCE_NAME")
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne // Member : N, Team : 1
    @JoinColumn(name = "TEAM_ID") // 객체 참조와 DB의 외래키 매핑핑
    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Member(){ }
}
