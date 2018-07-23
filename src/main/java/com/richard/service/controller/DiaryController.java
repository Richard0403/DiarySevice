package com.richard.service.controller;

/**
 * by Richard on 2017/9/24
 * desc:
 */

import com.mysql.jdbc.StringUtils;
import com.richard.service.constant.ErrorCode;
import com.richard.service.constant.RandomPicture;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.common.summary.DiarySummary;
import com.richard.service.domain.diary.Diary;
import com.richard.service.domain.diary.DiaryTag;
import com.richard.service.domain.diary.RepositoryDiary;
import com.richard.service.domain.diary.RepositoryDiaryTag;
import com.richard.service.domain.user.User;
import com.richard.service.service.DiaryService;
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
@RequestMapping(value = "/diary")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RepositoryDiary repositoryDiary;
    @Autowired
    private RepositoryDiaryTag repositoryDiaryTag;

    @ApiOperation(value = "获取用户日记列表", notes = "需要分页，需要token")
    @RequestMapping(value="/getUserDiary", method= RequestMethod.POST)
    public RestResult getUserDiary(@RequestBody Map<String, String> params,  HttpServletRequest request){
        int pageNo = Integer.parseInt(params.get("pageNo"));
        int pageSize = Integer.parseInt(params.get("pageSize"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        Map<String,Object> result = diaryService.getUserDiary(user, pageNo, pageSize);
        return RestGenerator.genResult(ErrorCode.OK, result, "success");
    }

    @ApiOperation(value = "获取公开日记列表", notes = "需要分页，需要token")
    @RequestMapping(value="/getPublicDiary", method= RequestMethod.POST)
    public RestResult getPublicDiary(@RequestBody Map<String, String> params,  HttpServletRequest request){
        int pageNo = Integer.parseInt(params.get("pageNo"));
        int pageSize = Integer.parseInt(params.get("pageSize"));

        List<Diary> diaryList = diaryService.getUserDiary(pageNo, pageSize);
        return RestGenerator.genResult(ErrorCode.OK, diaryList, "success");
    }

    @ApiOperation(value = "根据标签查询笔记", notes = "需要分页，需要token")
    @RequestMapping(value="/getDiaryByTag", method= RequestMethod.POST)
    public RestResult getDiaryByUserAndTag(@RequestBody Map<String, String> params,  HttpServletRequest request){
        int pageNo = Integer.parseInt(params.get("pageNo"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        long tagId = Long.parseLong(params.get("tagId"));

        DiaryTag diaryTag = repositoryDiaryTag.findOne(tagId);
        final User user = tokenUtil.getUserIdFromHttpReq(request);
        List<DiarySummary> diaryList = diaryService.getUserTagDiary(user, diaryTag, pageNo, pageSize);
        return RestGenerator.genResult(ErrorCode.OK, diaryList, "success");
    }

    @ApiOperation(value = "获取日记详情")
    @RequestMapping(value="/getDiaryDetail", method= RequestMethod.POST)
    public RestResult getDiaryDetail(@RequestBody Map<String, String> params){
        long id = Long.parseLong(params.get("id"));

        Diary diary = diaryService.getDiaryDetail(id);
        return RestGenerator.genResult(ErrorCode.OK, diary, "success");
    }

    @ApiOperation(value = "写日记")
    @RequestMapping(value="/addDiary", method= RequestMethod.POST)
    public RestResult addDiary(@RequestBody Map<String, String> params, HttpServletRequest request){
        final String title = params.get("title");
        final String content = params.get("content");
        //发布状态--> 0.私密 1.公开 2.部分公开
        final int pubStatus = Integer.parseInt(params.get("pubStatus"));
        //picture可以为空
        String picture = params.get("picture");
        final long diaryTagId = Integer.parseInt(params.get("tagId"));

        if(StringUtils.isNullOrEmpty(picture)){
            picture = RandomPicture.getRandomPicture();
        }
        final User user = tokenUtil.getUserIdFromHttpReq(request);
        boolean saveSuccess = diaryService.addDiary(user, title, content, pubStatus, picture, diaryTagId);
        if(saveSuccess){
            return RestGenerator.genSuccessResult(null, "发布成功");
        }else{
            return RestGenerator.genErrorResult("发布失败");
        }
    }

    @ApiOperation(value = "删除日记")
    @RequestMapping(value="/delUserDiary", method= RequestMethod.POST)
    public RestResult delUserDiary(@RequestBody Map<String, String> params, HttpServletRequest request){
        long id = Long.parseLong(params.get("id"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        Diary diary = repositoryDiary.findOne(id);
        if(diary.getUser().equals(user)){
            diaryService.delDiary(diary);
            return RestGenerator.genSuccessResult(null, "删除成功");
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }

    @ApiOperation(value = "设置公开状态")
    @RequestMapping(value="/setDiaryPubStatus", method= RequestMethod.POST)
    public RestResult setDiaryPubStatus(@RequestBody Map<String, String> params, HttpServletRequest request){
        long id = Long.parseLong(params.get("id"));
        int pubStatus = Integer.parseInt(params.get("pubStatus"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        Diary diary = repositoryDiary.findOne(id);
        if(diary.getUser().equals(user)){
            diaryService.setDiaryStatus(diary, pubStatus);
            return RestGenerator.genSuccessResult(null, "设置成功");
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }

    @ApiOperation(value = "更改笔记标签")
    @RequestMapping(value="/changeDiaryTag", method= RequestMethod.POST)
    public RestResult changeDiaryTag(@RequestBody Map<String, String> params, HttpServletRequest request){
        long id = Long.parseLong(params.get("id"));
        long tagId = Long.parseLong(params.get("tagId"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        final DiaryTag diaryTag = repositoryDiaryTag.findOne(tagId);
        Diary diary = repositoryDiary.findOne(id);
        if(diary.getUser().equals(user) && diaryTag.getUser().equals(user)){
            diaryService.changeDiaryTag(diary, diaryTag);
            return RestGenerator.genSuccessResult(null, "删除成功");
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }


}
