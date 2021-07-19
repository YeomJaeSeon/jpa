package book.store.service;

import book.store.domain.Address;
import book.store.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입(){
        Member member = new Member();
        member.setName("고객A");
        member.setAddress(new Address("서울", "가로수길", "12345"));

        Long memberId = memberService.createMember(member);
        Member findMember = memberService.findMemberById(memberId);

        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void 전체회원_조회(){
        //given
        Member member = new Member();
        member.setName("고객A");
        member.setAddress(new Address("서울", "가로수길", "12345"));
        Member member1 = new Member();
        member1.setName("고객B");
        member1.setAddress(new Address("광주", "충장로", "12345"));
        memberService.createMember(member);
        memberService.createMember(member1);

        //when
        List<Member> members = memberService.findAllMembers();

        assertThat(members.size()).isEqualTo(2);

    }

}