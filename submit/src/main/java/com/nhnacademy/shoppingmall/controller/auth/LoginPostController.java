package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 로그인 구현, session은 60분동안 유지됩니다.
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        String userId = req.getParameter("user_id");
        String userPassword = req.getParameter("user_password");
        User user = userService.doLogin(userId, userPassword);

        if(Objects.nonNull(user)) {
            req.getSession().setAttribute("userId", userId);
            req.getSession().setMaxInactiveInterval(60 * 60);
        }

        return "redirect:/index.do";
    }
}
