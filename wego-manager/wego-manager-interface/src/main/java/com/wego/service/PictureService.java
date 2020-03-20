package com.wego.service;


import com.wego.common.pojo.PictureResult;

public interface PictureService {
    PictureResult uploadPic(byte[] bytes, String extName);
}
