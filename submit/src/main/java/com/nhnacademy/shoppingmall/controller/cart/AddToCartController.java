package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/add.do")
public class AddToCartController implements BaseController {

    private ProductService productService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");
        HttpSession session = req.getSession(false);
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (Objects.isNull(cart)) {
            cart = new ArrayList<>();
        }

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProduct(productId);
        if(!cart.contains(product)) {
            cart.add(product);
        }

        session.setAttribute("cart", cart);

        return "redirect:/product/view.do?productId=" + productId;
    }
}
