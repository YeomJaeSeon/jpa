package book.store.controller;

import book.store.domain.Address;
import book.store.domain.Member;
import book.store.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid @ModelAttribute MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            return "member/createMemberForm";
        }
        // 컨트롤러에서 DTO를 엔티티로 바꾸는로직.
        Member member = new Member();
        member.setName(memberForm.getName());
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        member.setAddress(address);

        memberService.createMember(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);

        return "member/memberList";
    }
}
