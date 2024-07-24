package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/update.do")
public class ProductUpdatePostController implements BaseController {

    private static final String UPLOAD_PATH = "/Users/parkheejun/NHNacademy/Java/20240426/servlet-jsp-shoppingmall/src/main/webapp/resources/product";
    private static final String IMAGE_PATH = "/resources/product/";
    private ProductService productService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        productService = (ProductService) req.getServletContext().getAttribute("productService");
        String realPath = UPLOAD_PATH;
        int sizeLimit = 15 * 1024 * 1024;

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            MultipartRequest multipartRequest = new MultipartRequest(req, realPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());

            int productId = Integer.parseInt(multipartRequest.getParameter("productId"));
            String productName = multipartRequest.getParameter("productName");
            int productPrice = Integer.parseInt(multipartRequest.getParameter("productPrice"));
            int productStock = Integer.parseInt(multipartRequest.getParameter("productStock"));
            String productImg = "";
            if(Objects.nonNull(multipartRequest.getFilesystemName("productImg"))) {
                productImg = IMAGE_PATH + multipartRequest.getFilesystemName("productImg");
            }
            int categoryId = Integer.parseInt(multipartRequest.getParameter("categoryId"));

            Product product = new Product(productId, productName, productPrice, productStock, productImg, categoryId);
            productService.updateProduct(product);
            req.setAttribute("product", product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/admin/productPage.do";
    }
}
