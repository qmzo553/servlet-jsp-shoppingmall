package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/savedPoint.do")
public class MyPageSavedPointController implements BaseController {

    private SavedPointService savedPointService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        savedPointService = (SavedPointService) req.getServletContext().getAttribute("savedPointService");

        String userId = (String) req.getSession().getAttribute("userId");

        int currentPage = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = 10;// 한 페이지에 보여질 아이템 수
        Page<SavedPoint> savedPointPage = savedPointService.getOrders(userId, currentPage, pageSize);

        int pageBlock = 5; // 한 페이지 버튼 수
        int totalCount = (int) savedPointService.getOrderTotalCount(userId); // 총 데이터 갯수
        int pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1); // 전체 페이지 수
        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1; // 시작 버튼
        int endPage = startPage + pageBlock - 1; // 마지막 버튼
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        req.setAttribute("savedPointPage", savedPointPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);

        req.setAttribute("savedPoints", savedPointService);
        return "/shop/mypage/mypage_savedPoint";
    }
}
