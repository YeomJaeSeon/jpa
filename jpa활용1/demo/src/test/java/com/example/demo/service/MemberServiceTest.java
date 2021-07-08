package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    void 회원가입(){
        Member member = new Member();
        member.setName("염재선");

        Long saveId = memberService.join(member);
        Member findMember = memberService.findById(saveId);

        assertThat(member).isEqualTo(findMember);
    }
    // 테스트 종료시 트랜잭션이 커밋된다. (테스트에서 트랜잭션은 롤백되므로 커밋되어 flush가 되지않음..)

    @Test
    void 중복_회원가입_예외(){
        Member member1 = new Member();
        member1.setName("yeom");
        Member member2 = new Member();
        member2.setName("yeom");

        memberService.join(member1);

        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }


}