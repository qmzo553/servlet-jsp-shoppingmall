package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/user/update.do")
public class UserUpdatePostController implements BaseController {

    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");

        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userBirth = req.getParameter("userBirth");

        if(Objects.isNull(userId) || Objects.isNull(userName) || Objects.isNull(userPassword) || Objects.isNull(userBirth)) {
            throw new RuntimeException("id, name, password, birth를 모두 입력해주세요");
        }

        User user = userService.getUser(userId);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserBirth(userBirth);
        userService.updateUser(user);
        req.setAttribute("user", user);
        return "redirect:/mypage.do";
    }
}
