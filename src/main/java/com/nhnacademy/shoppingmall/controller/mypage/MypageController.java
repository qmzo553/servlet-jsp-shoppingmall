package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/mypage.do")
public class MypageController implements BaseController {

    private UserService userService;
    private AddressService addressService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        userService = (UserService) req.getServletContext().getAttribute("userService");
        addressService = (AddressService) req.getServletContext().getAttribute("addressService");
        HttpSession session = req.getSession();

        String userId = (String) session.getAttribute("userId");
        User user = userService.getUser(userId);
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        List<Address> addresses = addressService.getAddressesByUserId(userId);

        req.setAttribute("cart", cart);
        req.setAttribute("user", user);
        req.setAttribute("addresses", addresses);

        return "shop/mypage/mypage_form";
    }
}
