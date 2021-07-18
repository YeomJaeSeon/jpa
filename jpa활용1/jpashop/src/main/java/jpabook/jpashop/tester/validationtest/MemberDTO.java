package jpabook.jpashop.tester.validationtest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberDTO {

    @NotEmpty
    private String name;

    @Email
    private String email;
}
