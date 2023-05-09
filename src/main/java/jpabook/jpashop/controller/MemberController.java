package jpabook.jpashop.controller;

import jpabook.jpashop.SessionConst;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute("memberForm") MemberForm form, BindingResult bindingResult, HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "/members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setAddress(address);


        log.info("member={}", member);



        memberService.join(member);



        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }


}
