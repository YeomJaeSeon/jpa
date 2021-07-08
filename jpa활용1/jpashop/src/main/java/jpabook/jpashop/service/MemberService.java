package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
// jpa 에서는 모든데이터변경은 트랜잭션 내에서 실행되야한다.
// 이 애너테이션쓰면 퍼블릭 메서드는 기본적으로트랜잭션이 걸려들어간다.
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional // 조회만할것이 아니기떄문에 (이게 우선권을갖는다. 클래스레벨 에너테이션보다)
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //중복회원이면 예외터트림
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원조회
    // 전체
//    @Transactional(readOnly = true) // 조회만하는곳에선 좀더 성능최적화한다. (readOnly)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 단건
//    @Transactional(readOnly = true)
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
