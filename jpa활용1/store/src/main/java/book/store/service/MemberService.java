package book.store.service;

import book.store.domain.Member;
import book.store.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(Member member){
        Long memberId = memberRepository.save(member);
        return memberId;
    }

    public Member findMemberById(Long id){
        Member member = memberRepository.findOne(id);
        return member;
    }

    public List<Member> findAllMembers(){
        return memberRepository.findAll();
    }
}
