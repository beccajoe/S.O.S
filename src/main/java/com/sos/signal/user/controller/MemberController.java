package com.sos.signal.user.controller;

import com.sos.signal.user.dto.KakaoDTO;
import com.sos.signal.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
public class MemberController {

    @Autowired
    private MemberService ms;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/loginform", method = RequestMethod.GET)
    public String loginForm() {
        return "member/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
        System.out.println("#########" + code);
        String access_Token = ms.getAccessToken(code);
        KakaoDTO userInfo = ms.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
//        System.out.println("###age_range#### : " + userInfo.get("age_range"));
//        System.out.println("###email#### : " + userInfo.get("email"));

        session.setAttribute("kakaoE", userInfo.getU_email());
        session.setAttribute("kakaoA", userInfo.getU_age_range());

        return "common/logout_main";
    }

    @RequestMapping(value = "/chat-loginform", method=RequestMethod.GET)
    public String chatlogin() {
        return "chat/chat_login";
    }

    @RequestMapping(value = "/chat_login", method = RequestMethod.GET)
    public String chatkakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
//        System.out.println("#########" + code);
//        String access_Token = ms.getAccessToken(code);
//        KakaoDTO userInfo = ms.getUserInfo(access_Token);
//        System.out.println("###access_Token#### : " + access_Token);
////        System.out.println("###age_range#### : " + userInfo.get("age_range"));
////        System.out.println("###email#### : " + userInfo.get("email"));
//
//        session.invalidate();
//        session.setAttribute("kakaoE", userInfo.getU_email());
//        session.setAttribute("kakaoA", userInfo.getU_age_range());

        return "chat/room";
    }

    @RequestMapping(value = "/admin-login", method = RequestMethod.GET)
    public String adminLogin() {
        return "member/login_form";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "common/logout_main"; // 로그아웃 후 리다이렉트할 URL
    }

    @RequestMapping("/admin/logout")
    public String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "common/main"; // 로그아웃 후 리다이렉트할 URL
    }
}