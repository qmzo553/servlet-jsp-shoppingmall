package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/delete.do")
public class OrderProductDeleteController implements BaseController {

    private ProductService productService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = productService.getProduct(productId);

        HttpSession session = req.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        cart.remove(product);

        session.setAttribute("cart", cart);

        return "redirect:/order/view.do";
    }
}
