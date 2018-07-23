package com.richard.service.controller;

import com.mysql.jdbc.StringUtils;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.diary.Comment;
import com.richard.service.domain.diary.RepositoryCmt;
import com.richard.service.domain.user.User;
import com.richard.service.service.CmtService;
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


/**
 * @author Richard
 * desc 评论
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private CmtService cmtService;
    @Autowired
    private RepositoryCmt repositoryCmt;
    @Autowired
    private DiaryService diaryService;

    @ApiOperation(value = "添加评论， 回复评论")
    @RequestMapping(value="/addComment", method= RequestMethod.POST)
    public RestResult commentDiary(@RequestBody Map<String, String> params, HttpServletRequest request){
        final String content = params.get("content");
        final long diaryId = Long.parseLong(params.get("diaryId"));
        final String toUserId = params.get("toUserId");
        final String toCommentId = params.get("toCommentId");
        final User user = tokenUtil.getUserIdFromHttpReq(request);
        if(cmtService.commentDiary(diaryId, content, user, toCommentId, toUserId)){
            return RestGenerator.genSuccessResult(null, "评论成功");
        }else{
            return RestGenerator.genErrorResult("评论失败");
        }
    }
    @ApiOperation(value = "获取评论列表")
    @RequestMapping(value="/getComment", method= RequestMethod.POST)
    public RestResult commentDiary(@RequestBody Map<String, String> params){
        long diaryId = Long.parseLong(params.get("diaryId"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        int pageNo = Integer.parseInt(params.get("pageNo"));
        List<Comment> comments = cmtService.getComment(diaryId, pageSize, pageNo);
        return RestGenerator.genSuccessResult(comments, "评论成功");
    }

    @ApiOperation(value = "获取简单评论列表")
    @RequestMapping(value="/getSimpleComment", method= RequestMethod.POST)
    public RestResult getSimpleComment(@RequestBody Map<String, String> params){
        long diaryId = Long.parseLong(params.get("diaryId"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        int pageNo = Integer.parseInt(params.get("pageNo"));
        List<Comment> comments = cmtService.getSimpleComment(diaryId, pageSize, pageNo);
        return RestGenerator.genSuccessResult(comments, "评论成功");
    }


    @ApiOperation(value = "删除评论")
    @RequestMapping(value="/delComment", method= RequestMethod.POST)
    public RestResult delComment(@RequestBody Map<String, String> params,  HttpServletRequest request){
        final long commentId = Long.parseLong(params.get("commentId"));
        Comment comment = repositoryCmt.findOne(commentId);
        User user = tokenUtil.getUserIdFromHttpReq(request);
        if(comment.getUser().equals(user)){
            cmtService.delComment(comment);
            return RestGenerator.genSuccessResult(null, "删除成功");
        }else{
            return RestGenerator.genErrorResult("无权操作");
        }
    }


    @ApiOperation(value = "笔记、评论点赞")
    @RequestMapping(value="/addPraise", method= RequestMethod.POST)
    public RestResult articlePraise(@RequestBody Map<String, String> params, HttpServletRequest request){
        final String diaryId = params.get("diaryId");
        final String commentId = params.get("commentId");

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        boolean isPraise = false;
        if(!StringUtils.isNullOrEmpty(commentId)){
            isPraise = cmtService.praiseComment(user, commentId);
        }else if(!StringUtils.isNullOrEmpty(diaryId)){
            isPraise = diaryService.praiseDiary(user, diaryId);
        }
        if(isPraise){
            return RestGenerator.genSuccessResult(null, "点赞成功");
        }else{
            return RestGenerator.genSuccessResult(null, "取消点赞成功");
        }
    }

}
