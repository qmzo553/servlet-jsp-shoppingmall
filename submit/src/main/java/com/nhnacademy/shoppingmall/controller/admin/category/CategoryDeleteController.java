package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category/delete.do")
public class CategoryDeleteController implements BaseController {

    private CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        categoryService.deleteCategory(categoryId);

        return "redirect:/admin/categoryPage.do";
    }
}
