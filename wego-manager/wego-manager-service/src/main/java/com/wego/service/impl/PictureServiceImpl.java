package com.wego.service.impl;

import com.wego.common.pojo.PictureResult;
import com.wego.service.utils.FastDFSClient;
import com.wego.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    public PictureResult uploadPic(byte[] bytes, String extName) {
        PictureResult result = new PictureResult();
        if (bytes.length == 0) {
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        FastDFSClient client = null;
        try {
            client = new FastDFSClient("classpath:properties/fastdfs.conf");
            String url = IMAGE_SERVER_URL + client.uploadFile(bytes, extName);
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }
        return result;
    }
}
