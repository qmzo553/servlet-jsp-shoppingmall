package com.nhnacademy.shoppingmall.controller.exception;

import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.category.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shoppingmall.category.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.common.mvc.exception.ControllerNotFoundException;
import com.nhnacademy.shoppingmall.common.mvc.exception.PageNotFoundException;
import com.nhnacademy.shoppingmall.order.exception.OrderAlreadyExistsException;
import com.nhnacademy.shoppingmall.order.exception.OrderNotFoundException;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointAlreadyExistsException;
import com.nhnacademy.shoppingmall.point.savedPoint.exception.SavedPointNotFoundException;
import com.nhnacademy.shoppingmall.point.usedPoint.exception.UsedPointAlreadExistsException;
import com.nhnacademy.shoppingmall.point.usedPoint.exception.UsedPointNotFoundException;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler {

    private static final String STATUS_CODE = "status_code";

    public static void execute(HttpServletRequest req, HttpServletResponse resp, Exception e) throws IOException, ServletException {

        if (e instanceof UserNotFoundException
                || e instanceof ProductNotFoundException
                || e instanceof CategoryNotFoundException
                || e instanceof OrderNotFoundException
                || e instanceof PageNotFoundException
                || e instanceof SavedPointNotFoundException
                || e instanceof UsedPointNotFoundException
                || e instanceof AddressNotFoundException
                || e instanceof ControllerNotFoundException) {
            req.setAttribute(STATUS_CODE, HttpServletResponse.SC_NOT_FOUND);
        } else if (e instanceof UserAlreadyExistsException
                || e instanceof ProductAlreadyExistsException
                || e instanceof CategoryAlreadyExistsException
                || e instanceof OrderAlreadyExistsException
                || e instanceof SavedPointAlreadyExistsException
                || e instanceof UsedPointAlreadExistsException) {
            req.setAttribute(STATUS_CODE, HttpServletResponse.SC_CONFLICT);
        }  else if (e instanceof IllegalArgumentException) {
            req.setAttribute(STATUS_CODE, HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof RuntimeException) {
            req.setAttribute(STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        req.setAttribute("exception_type", e.getClass().getName());
        req.setAttribute("message", e.getMessage());
        RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
        rd.forward(req, resp);
    }
}
