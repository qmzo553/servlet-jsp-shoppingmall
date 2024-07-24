package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
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
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/view.do")
public class ProductViewController implements BaseController {

    private ProductService productService;
    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");
        productService = (ProductService) req.getServletContext().getAttribute("productService");

        HttpSession session = req.getSession();
        if(Objects.nonNull(session) && Objects.nonNull(session.getAttribute("userId"))) {
            User user = userService.getUser(session.getAttribute("userId").toString());
            req.setAttribute("user", user);
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProduct(productId);
        req.setAttribute("product", product);

        Cookie recentProductsCookie;
        if(Objects.isNull(CookieUtils.getCookie(req, "recentProducts"))) {
            recentProductsCookie = new Cookie("recentProducts", "");
        } else {
            recentProductsCookie = CookieUtils.getCookie(req, "recentProducts");
        }

        String recentProducts = recentProductsCookie.getValue();

        if (recentProducts == null) {
            recentProducts = productId + "";
        } else {
            List<String> products = new ArrayList<>(Arrays.asList(recentProducts.split(":")));

            products.remove(productId + "");

            products.add(0, productId + "");

            if (products.size() > 5) {
                products = products.subList(0, 5);
            }
            recentProducts = String.join(":", products);
        }

        recentProductsCookie.setValue(recentProducts);
        recentProductsCookie.setMaxAge(60 * 60 * 24 * 1);
        recentProductsCookie.setPath("/");
        resp.addCookie(recentProductsCookie);

        return "/shop/product/product_view";
    }
}
