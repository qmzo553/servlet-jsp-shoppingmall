package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.GET,value = "/logoutAction.do")
public class LogoutController implements BaseController {
    // 로그아웃 구현
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        if(Objects.nonNull(session)) {
            session.invalidate();
        }

        Cookie cookie = CookieUtils.getCookie(req, "JSESSIONID");
        if(Objects.nonNull(cookie)) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }

        return "redirect:/index.do";
    }

}
