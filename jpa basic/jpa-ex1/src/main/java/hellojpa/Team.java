package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "TEAM_SEQ_NAME",
        sequenceName = "TEAM_SEQ"
)
public class Team {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TEAM_SEQ_NAME"
    )
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
     //DB에 영향 X, 단순히조회만가능

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
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
