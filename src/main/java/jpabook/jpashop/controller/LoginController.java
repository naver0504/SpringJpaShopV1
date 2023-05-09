package jpabook.jpashop.controller;

import jpabook.jpashop.argumentresolver.Login;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
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

        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginForm form, HttpServletRequest request,BindingResult bindingResult, Model model
            , @RequestParam(defaultValue = "/") String redirectURL) {


        Member loginMember = memberRepository.findByLoginId(form.getLoginId());

        HttpSession session = request.getSession();
        session.setAttribute("loginMember",loginMember);

        return "redirect:"+redirectURL;

    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();

        return "redirect:/";

    }




}
