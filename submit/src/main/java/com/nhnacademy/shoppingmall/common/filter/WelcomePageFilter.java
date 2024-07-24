package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(
        filterName = "welcomePageFilter",
        urlPatterns = "/"
)
public class WelcomePageFilter extends HttpFilter {

    private static final String REDIRECT_URL = "/index.do";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // /요청이 오면 welcome page인 index.do redirect 합니다.
        log.info("WelcomePageFilter");
        String method = req.getMethod();
        String requestURI = req.getRequestURI();

        if(method.equals("GET") && requestURI.equals("/")) {
            res.sendRedirect(REDIRECT_URL);
            return;
        }

        chain.doFilter(req, res);
    }
}
