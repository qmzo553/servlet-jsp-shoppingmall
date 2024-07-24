package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/usedPoint.do")
public class MyPageUsedPointController implements BaseController {

    private UsedPointService usedPointService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        usedPointService = (UsedPointService) req.getServletContext().getAttribute("usedPointService");
        String userId = (String) req.getSession().getAttribute("userId");

        int currentPage = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = 10;// 한 페이지에 보여질 아이템 수
        Page<UsedPoint> usedPointPage = usedPointService.getUsedPoints(userId, currentPage, pageSize);

        int pageBlock = 5; // 한 페이지 버튼 수
        int totalCount = (int) usedPointService.getUsedPointTotalCount(userId); // 총 데이터 갯수
        int pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1); // 전체 페이지 수
        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1; // 시작 버튼
        int endPage = startPage + pageBlock - 1; // 마지막 버튼
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        req.setAttribute("usedPointPage", usedPointPage);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);

        return "shop/mypage/mypage_usedPoint";
    }
}
