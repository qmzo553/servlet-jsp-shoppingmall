package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {

    private ProductService productService;
    List<String> recentProductsList;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");


        int currentPage = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = 10;// 한 페이지에 보여질 아이템 수
        Page<Product> productPage = productService.getProducts(currentPage, pageSize);

        int pageBlock = 10; // 한 페이지 버튼 수
        int totalCount = (int) productService.getProductTotalCount(); // 총 데이터 갯수
        int pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1); // 전체 페이지 수
        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1; // 시작 버튼
        int endPage = startPage + pageBlock - 1; // 마지막 버튼
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        Cookie recentProductsCookie = CookieUtils.getCookie(req, "recentProducts");
        if(Objects.nonNull(recentProductsCookie)) {
            if(!recentProductsCookie.getValue().equals("")) {
                recentProductsList = List.of(Objects.requireNonNull(recentProductsCookie.getValue()).trim().split(":"));
                List<Product> recentProducts = new ArrayList<>();
                for(String productId : recentProductsList) {
                    recentProducts.add(productService.getProduct(Integer.parseInt(productId)));
                }
                req.setAttribute("recentProducts", recentProducts);
            }
        }

        req.setAttribute("productPage", productPage);
        req.setAttribute("pageBlock", pageBlock);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);
        req.setAttribute("pageSize", pageSize);


        return "shop/main/index";
    }
}