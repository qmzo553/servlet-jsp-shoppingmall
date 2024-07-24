package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/categoryPage.do")
public class CategoryPageController implements BaseController {

    private CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        List<Category> categories = categoryService.getRootCategories();
        req.setAttribute("categories", categories);

        return "/admin/category/category_form";
    }
}
