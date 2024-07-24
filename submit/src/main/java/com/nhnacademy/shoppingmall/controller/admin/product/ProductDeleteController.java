package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/delete.do")
public class ProductDeleteController implements BaseController {

    private ProductService productService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");

        int productId = Integer.parseInt(req.getParameter("productId"));
        productService.deleteProduct(productId);

        // 쿠키에서 최근 본 상품 목록 가져오기
        Cookie recentProductsCookie = CookieUtils.getCookie(req, "recentProducts");
        if (recentProductsCookie != null) {
            String products = recentProductsCookie.getValue();
            // ':'로 구분된 상품 ID 목록에서 삭제할 productId를 제외하고 새 목록 생성
            String updatedProducts = Arrays.stream(products.split(":"))
                    .filter(id -> !id.equals(String.valueOf(productId)))
                    .collect(Collectors.joining(":"));

            // 수정된 상품 목록으로 쿠키 업데이트
            recentProductsCookie.setValue(updatedProducts);
            recentProductsCookie.setPath("/");  // 쿠키 경로 설정
            recentProductsCookie.setMaxAge(60 * 60 * 24 * 30);  // 쿠키 만료기간 설정 (예: 30일)
            resp.addCookie(recentProductsCookie);
        }

        return "redirect:/admin/productPage.do";
    }
}
