package com.wego.controller;

import com.wego.common.pojo.PictureResult;
import com.wego.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PictureResult uploadFile(MultipartFile uploadFile) {
        String fileName = uploadFile.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        PictureResult result = null;
        try {
            result = pictureService.uploadPic(uploadFile.getBytes(), extName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
