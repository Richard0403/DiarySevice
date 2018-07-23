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
import com.richard.service.service.DiaryTagService;
import com.richard.service.utils.RestGenerator;
import com.richard.service.utils.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/diaryTag")
public class DiaryTagController {
    @Autowired
    private DiaryTagService diaryTagService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RepositoryDiaryTag repositoryDiaryTag;

    @ApiOperation(value = "添加标签")
    @RequestMapping(value="/addDiaryTag", method= RequestMethod.POST)
    public RestResult addDiaryTag(@RequestBody Map<String, String> params,  HttpServletRequest request){
        String name = params.get("tagName");
        String description = params.get("description");
        String picture = params.get("picture");

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        DiaryTag result = diaryTagService.addDiaryTag(name, description, picture, user);
        return RestGenerator.genResult(ErrorCode.OK, result, "添加成功");
    }

    @ApiOperation(value = "修改标签")
    @RequestMapping(value="/editDiaryTag", method= RequestMethod.POST)
    public RestResult editDiaryTag(@RequestBody Map<String, String> params,  HttpServletRequest request){
        long id = Long.parseLong(params.get("id"));
        String name = params.get("tagName");
        String description = params.get("description");
        String picture = params.get("picture");

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        DiaryTag diaryTag = repositoryDiaryTag.findOne(id);
        if(!diaryTag.getUser().equals(user)){
            DiaryTag result = diaryTagService.fixDiaryTag(diaryTag, name, description, picture);
            return RestGenerator.genResult(ErrorCode.OK, result, "添加成功");
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }

    @ApiOperation(value = "删除标签")
    @RequestMapping(value="/delDiaryTag", method= RequestMethod.POST)
    public RestResult delDiaryTag(@RequestBody Map<String, String> params,  HttpServletRequest request){
        long id = Long.parseLong(params.get("id"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        DiaryTag diaryTag = repositoryDiaryTag.findOne(id);
        if(!diaryTag.getUser().equals(user)){
            DiaryTag result = diaryTagService.delDiaryTag(diaryTag);
            if(result != null){
                return RestGenerator.genResult(ErrorCode.OK, null, "删除成功");
            }else{
                return RestGenerator.genErrorResult("标签下存在笔记，不可删除");
            }
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }

    @ApiOperation(value = "查询标签")
    @RequestMapping(value="/getUserTag", method= RequestMethod.POST)
    public RestResult getDiaryByUserAndTag(@RequestBody Map<String, String> params,  HttpServletRequest request){
        int pageNo = Integer.parseInt(params.get("pageNo"));
        int pageSize = Integer.parseInt(params.get("pageSize"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        List<DiaryTagSummary> result = diaryTagService.getUserTag(user, pageNo, pageSize);
        return RestGenerator.genResult(ErrorCode.OK, result, "success");
    }



}
