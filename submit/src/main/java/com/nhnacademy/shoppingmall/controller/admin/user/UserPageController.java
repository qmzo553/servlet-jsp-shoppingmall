package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/userPage.do")
public class UserPageController implements BaseController {

    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");

        List<User> admins = userService.getUsersByAuth(User.Auth.ROLE_ADMIN);

        int currentPage = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = 10;// 한 페이지에 보여질 아이템 수
        Page<User> userPage = userService.getUsers(User.Auth.ROLE_USER, currentPage, pageSize);

        int pageBlock = 5; // 한 페이지 버튼 수
        int totalCount = (int) userService.getAuthTotalCount(User.Auth.ROLE_USER); // 총 데이터 갯수
        int pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1); // 전체 페이지 수
        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1; // 시작 버튼
        int endPage = startPage + pageBlock - 1; // 마지막 버튼
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        req.setAttribute("userPage", userPage);
        req.setAttribute("pageBlock", pageBlock);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);
        req.setAttribute("currentPage", currentPage);

        req.setAttribute("admins", admins);

        return "/admin/user/user_form";
    }
}
