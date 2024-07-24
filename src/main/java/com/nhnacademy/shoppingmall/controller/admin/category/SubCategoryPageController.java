package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/subCategoryPage.do")
public class SubCategoryPageController implements BaseController {

    private CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        List<Category> subCategories = categoryService.getChildCategories(categoryId);

        req.setAttribute("subCategories", subCategories);
        return "redirect:/admin/categoryPage.do";
    }
}
