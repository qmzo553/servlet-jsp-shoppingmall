package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.POST,value = "/user/register.do")
public class RegisterPostController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");

        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userBirth = req.getParameter("userBirth");

        // empty check
        if(Objects.isNull(userId) || Objects.isNull(userName) || Objects.isNull(userPassword) || Objects.isNull(userBirth)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "redirect:/login.do";
        }

        User user = new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER);
        userService.saveUser(user);
        HttpSession session = req.getSession();
        session.setAttribute("userId", userId);

        return "redirect:/index.do";
    }
}
