package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/add.do")
public class AddCategoryController implements BaseController {

    private CategoryService categoryService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        String categoryName = req.getParameter("categoryName");
        String parentCategoryName = req.getParameter("parentCategoryName");
        Category newCategory;
        if(parentCategoryName.equals("없음")) {
            newCategory = new Category(categoryName, 0);
        } else {
            Category parentCategory = categoryService.getCategory(parentCategoryName);
            newCategory = new Category(categoryName, parentCategory.getCategoryId());
        }

        categoryService.saveCategory(newCategory);

        return "redirect:/admin/categoryPage.do";
    }
}
