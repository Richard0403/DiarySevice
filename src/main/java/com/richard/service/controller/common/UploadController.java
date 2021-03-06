package com.richard.service.controller.common;

import com.mysql.jdbc.StringUtils;
import com.richard.service.constant.ErrorCode;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.user.User;
import com.richard.service.service.common.UploadService;
import com.richard.service.utils.RestGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * by Richard on 2017/8/31
 * desc:
 */
@RestController
@RequestMapping(value = "/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;


    @ApiOperation(value = "上传普通文件", notes = "多文件上传")
    @RequestMapping(value="/uploadFile", method= RequestMethod.POST)
    public RestResult uploadFile(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("files");
        List<String> urls = uploadService.uploadFile(files);
        return RestGenerator.genResult(ErrorCode.OK, urls,"Success");
    }

    @ApiOperation(value = "上传网络文件", notes = "多文件上传")
    @RequestMapping(value="/uploadNetFile", method= RequestMethod.POST)
    public RestResult uploadFile(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String urlParam = params.get("pictures");
        if(!StringUtils.isNullOrEmpty(urlParam)){
            String url[] = urlParam.split(",");
            List<String> resultUrls = uploadService.uploadUrlFile(Arrays.asList(url));
            return RestGenerator.genResult(ErrorCode.OK, resultUrls,"Success");
        }else{
            return RestGenerator.genErrorResult("参数错误");
        }
    }
}
