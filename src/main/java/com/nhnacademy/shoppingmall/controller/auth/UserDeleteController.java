package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value ="/user/delete.do")
public class UserDeleteController implements BaseController {

    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");
        String userId = req.getParameter("userId");
        userService.deleteUser(userId);

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
