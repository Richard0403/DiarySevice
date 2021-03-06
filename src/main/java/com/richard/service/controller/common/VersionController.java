package com.richard.service.controller.common;

import com.richard.service.constant.ErrorCode;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.common.summary.VersionSummary;
import com.richard.service.domain.version.Version;
import com.richard.service.service.common.VersionService;
import com.richard.service.utils.RestGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author Richard
 * desc 版本号相关信息
 */
@RestController
@RequestMapping(value = "/version")
public class VersionController {
    @Autowired
    private VersionService versionService;


    @ApiOperation(value = "版本号", notes = "获取当前最新版本号")
    @RequestMapping(value="/getVersion", method= RequestMethod.POST)
    public RestResult getVersion(@RequestBody Map<String, String> params){
        try {
            Version version = versionService.getVersion();
            return RestGenerator.genResult(ErrorCode.OK, version, "success");
        }catch (Exception e){
            return RestGenerator.genErrorResult("请求错误");
        }
    }

    @RequestMapping(value = "/getVersionId")
    public RestResult getVersionId(@RequestBody Map<String, Object> params){
        int isForce = Integer.parseInt(params.get("isForce").toString());
        List<VersionSummary> versions = versionService.getVersion(isForce);
        return RestGenerator.genResult(ErrorCode.OK, versions, "success");
    }
    @RequestMapping(value = "/getVersionPage")
    public RestResult getVersionPage(@RequestBody Map<String, Object> params){
        int pageNo = Integer.parseInt(params.get("pageNo").toString());
        int pageSize = Integer.parseInt(params.get("pageSize").toString());
        List<VersionSummary> versions = versionService.getVersions(pageSize,pageNo);
        return RestGenerator.genResult(ErrorCode.OK, versions, "success");
    }
}
