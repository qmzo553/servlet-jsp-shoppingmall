package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(
        filterName = "adminCheckFilter",
        urlPatterns = "/admin/*"
)
public class AdminCheckFilter extends HttpFilter {

    private UserService userService;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리
        userService = (UserService) req.getServletContext().getAttribute("userService");


        log.info("AdminCheckFilter");
        HttpSession session = req.getSession(false);
        if(Objects.isNull(session)) {
            res.sendRedirect("/login.do");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        DbConnectionThreadLocal.initialize();
        User user = userService.getUser(userId);
        DbConnectionThreadLocal.reset();
        if(!"ROLE_ADMIN".equals(user.getUserAuth().name())) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(req, res);
    }
}
