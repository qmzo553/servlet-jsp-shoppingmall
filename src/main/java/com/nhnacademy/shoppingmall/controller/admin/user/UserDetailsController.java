package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user/details.do")
public class UserDetailsController implements BaseController {

    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");

        String userId = req.getParameter("userId");
        User user = userService.getUser(userId);

        req.setAttribute("user", user);
        return "/admin/user/user_detail";
    }
}
