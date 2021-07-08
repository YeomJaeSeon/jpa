package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // test에서는 롤백함 -> flush를 안하닌까 DB로 쿼리가 날라가지않는다.
class MemberServiceTest {

    @Autowired
    MemberService memberService; //필드주입으로 그냥 간단하게 (테스트닌까)

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void 회원가입(){
        Member member = new Member();
        member.setName("염재선");

        Long saveId = memberService.join(member);
        System.out.println("//==아래 insert==//");
//        em.flush(); // flush로 영속성컨텍스트의 변경사항 DB로쿼리날려도 롤백되므로 DB 영향없다
        Member findMember = memberService.findOne(saveId);

        assertThat(member).isEqualTo(findMember); // 같은 트랜잭션내에선 같은 EntityManager를 사용하도록 스프링이 제공한다. 그렇기에 같은 EM이므로 같은 영솓성컨텍스트에 접근한다.
        // 하나의 영속성컨텍스트에 잇는 1차캐싱된 녀석을 꺼낸것이므로 당연히같다. (같은 트랜잭션 내에선 동일성을 보장한다.)
    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");
        memberService.join(member1);

        //when then
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    }
}