package com.nhnacademy.shoppingmall.controller.address;

import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/address/delete.do")
public class DeleteAddressController implements BaseController {

    private AddressService addressService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        addressService = (AddressService) req.getServletContext().getAttribute("addressService");

        int addressId = Integer.parseInt(req.getParameter("addressId"));

        addressService.deleteAddressByAddressId(addressId);

        return "redirect:/mypage.do";
    }
}
