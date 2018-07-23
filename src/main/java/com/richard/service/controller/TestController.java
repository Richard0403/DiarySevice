package com.richard.service.controller;

/**
 * by Richard on 2017/9/24
 * desc:
 */

import com.richard.service.constant.ErrorCode;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.common.summary.DiaryTagSummary;
import com.richard.service.domain.diary.DiaryTag;
import com.richard.service.domain.diary.RepositoryDiaryTag;
import com.richard.service.domain.user.User;
import com.richard.service.domain.version.RepositoryVersion;
import com.richard.service.domain.version.Version;
import com.richard.service.service.DiaryTagService;
import com.richard.service.utils.RestGenerator;
import com.richard.service.utils.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private RepositoryVersion versionRep;

    @ApiOperation(value = "测试")
    @RequestMapping(value="/getVersion", method= RequestMethod.GET)
    public RestResult getVersion(@RequestParam("name") String name, HttpServletRequest request){
        Version version = versionRep.findByVersionName(name);
        return RestGenerator.genResult(ErrorCode.OK, version, "获取成功");
    }

    @ApiOperation(value = "测试")
    @RequestMapping(value="/addVersion", method= RequestMethod.GET)
    public RestResult addVersion(@RequestParam("name") String name, HttpServletRequest request){
        Version version = new Version();
        version.setVersionName(name);
        versionRep.save(version);
        version = versionRep.findByVersionName(name);
        return RestGenerator.genResult(ErrorCode.OK, version, "添加成功");
    }
}
