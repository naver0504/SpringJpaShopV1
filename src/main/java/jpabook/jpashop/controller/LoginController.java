package jpabook.jpashop.controller;

import jpabook.jpashop.ConstString;
import jpabook.jpashop.argumentresolver.Login;
import jpabook.jpashop.controller.dto.LoginDTO;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String Home(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "/notLoginHome";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/login")
    public String loginMember(Model model) {

        LoginDTO loginForm = new LoginDTO();
        model.addAttribute("loginForm", loginForm);
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginDTO form, HttpServletRequest request, BindingResult bindingResult, Model model
            , @RequestParam(defaultValue = "/") String redirectURL) {

        if(bindingResult.hasErrors())
            return "login/loginForm";
        Optional<Member> loginMember = memberRepository.findByLoginForm(form);

        if(loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        log.info("redirectURL ={}", redirectURL);

        HttpSession session = request.getSession();

        session.setAttribute(ConstString.LOGIN_MEMBER,loginMember.get());

        return "redirect:" + redirectURL;

    }




    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();

        return "redirect:/";

    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }






}
