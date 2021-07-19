package mysql1.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원등록(){
        Member member = new Member();
        member.setName("회원1");

        memberRepository.save(member);
        Member resultMember = memberRepository.findOne(member.getId());

        Assertions.assertThat(resultMember).isEqualTo(member);
    }
}