package com.nhnacademy.shoppingmall.controller.file;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/file/upload.do")
public class FileUploadController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        <form action="/file/upload.do" method="post" enctype="multipart/form-data">
//                Select image to upload:
//                <input type="file" name="file" id="file">
//                <input type="submit" value="Upload Image" name="submit">
//            </form>
        return "";
    }
}
