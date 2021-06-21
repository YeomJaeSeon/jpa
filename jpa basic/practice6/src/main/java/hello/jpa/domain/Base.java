package hello.jpa.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Base {

    private LocalDate createdDate;

    private LocalDate modifiedDate;
}
