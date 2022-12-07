package com.smchoi.matzip.controller;

import com.smchoi.matzip.entities.member.UserEntity;
import com.smchoi.matzip.services.MemberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller(value = "com.smchoi.matzip.controller.MemberController")
@RequestMapping(value = "member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "kakao", produces = MediaType.TEXT_PLAIN_VALUE)
    public ModelAndView getKakao(@RequestParam(value = "code") String code,
                                 @RequestParam(value = "error", required = false) String error,
                                 @RequestParam(value = "error_discription", required = false) String errorDescription,
                                 HttpSession session) throws IOException {


        String accessToken = this.memberService.getKakaoAccessToken(code);
        UserEntity user = this.memberService.getKakaoUserInfo(accessToken);
        session.setAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView("member/kakao");
        return modelAndView;
    }

    @GetMapping(value = "logout")
    public ModelAndView getLogout(HttpSession session) {
        session.setAttribute("user", null);
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}
