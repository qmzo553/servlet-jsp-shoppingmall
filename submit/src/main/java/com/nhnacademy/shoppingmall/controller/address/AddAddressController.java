package com.nhnacademy.shoppingmall.controller.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/address/add.do")
public class AddAddressController implements BaseController {

    private AddressService addressService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        addressService = (AddressService) req.getServletContext().getAttribute("addressService");

        String userId = req.getSession().getAttribute("userId").toString();
        String addressName = req.getParameter("addressName");
        String addressDetail = req.getParameter("addressDetail");

        if(addressName == null || addressDetail == null || addressName.isEmpty() || addressDetail.isEmpty()) {
            throw new IllegalArgumentException("주소와 상세주소를 확인해주세요");
        }


        Address address = new Address(addressName, addressDetail, userId);
        addressService.saveAddress(address);
        return "redirect:/mypage.do";
    }
}
