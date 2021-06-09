package hellojpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "TEAM_SEQ_NAME",
        sequenceName = "TEAM_SEQ",
        initialValue = 1
)
public class Team {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TEAM_SEQ_NAME"
    )
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

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
