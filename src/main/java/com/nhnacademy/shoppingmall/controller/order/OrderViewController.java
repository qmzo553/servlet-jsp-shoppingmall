package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value ="/order/view.do")
public class OrderViewController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        req.setAttribute("cart", cart);

        return "/shop/order/order_view";
    }
}
