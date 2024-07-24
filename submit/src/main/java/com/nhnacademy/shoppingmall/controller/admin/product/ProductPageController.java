package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value ="/admin/productPage.do")
public class ProductPageController implements BaseController {

    private ProductService productService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");

        int currentPage = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = 10;// 한 페이지에 보여질 아이템 수
        Page<Product> productPage = productService.getProducts(currentPage, pageSize);

        int pageBlock = 5; // 한 페이지 버튼 수
        int totalCount = (int) productService.getProductTotalCount(); // 총 데이터 갯수
        int pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1); // 전체 페이지 수
        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1; // 시작 버튼
        int endPage = startPage + pageBlock - 1; // 마지막 버튼
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        req.setAttribute("productPage", productPage);
        req.setAttribute("pageBlock", pageBlock);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);
        req.setAttribute("currentPage", currentPage);

        return "/admin/product/product_form";
    }
}
