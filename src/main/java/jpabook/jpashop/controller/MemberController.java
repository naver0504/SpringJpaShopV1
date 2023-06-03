package jpabook.jpashop.controller;

import jpabook.jpashop.controller.dto.EmailAuthDTO;
import jpabook.jpashop.controller.dto.EmailDTO;
import jpabook.jpashop.controller.dto.MemberDTO;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.EmailService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();


    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberDTO());
        return "/members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute("memberForm") MemberDTO form, BindingResult bindingResult) {
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





        return "redirect:/members/emailSend";
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

    @GetMapping("/emailSend")
    public String mailSend(Model model) throws MessagingException {
        model.addAttribute("emailDTO", new EmailDTO());
        return "/email/emailSend";
    }
    @PostMapping("/emailSend")
    public String mailSendAuth(@Validated @ModelAttribute EmailDTO emailDTO, BindingResult bindingResult, Model model) throws MessagingException {
        String authCode = emailService.sendEmail(emailDTO.getEmail());
        map.put(emailDTO.getEmail(), authCode);
        EmailAuthDTO emailAuthDTO = new EmailAuthDTO();
        emailAuthDTO.setEmail(emailDTO.getEmail());
        model.addAttribute("emailAuthDTO", emailAuthDTO);
        return "/email/emailConfirm";
    }

    @PostMapping("/emailConfirm")
    public String mailConfirm(@ModelAttribute EmailAuthDTO emailAuthDTO) {
        if (map.get(emailAuthDTO.getEmail()).equals(emailAuthDTO.getAuth())) {
            return "redirect:/";
        }

        log.info("Wrong auth");
        return "redirect:/";
    }




}
