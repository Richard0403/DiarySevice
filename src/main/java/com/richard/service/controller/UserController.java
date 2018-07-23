package com.richard.service.controller;

import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.user.User;
import com.richard.service.service.UserService;
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
 * desc 用户信息相关
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;



    @ApiOperation(value = "关注 收藏")
    @RequestMapping(value="/addFollow", method= RequestMethod.POST)
    public RestResult follow(@RequestBody Map<String, String> params, HttpServletRequest request){
        final long resourceId = Long.parseLong(params.get("resourceId"));
        final int followType = Integer.parseInt(params.get("followType"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        if(userService.addFollow(user, resourceId, followType)){
            return RestGenerator.genSuccessResult(null, "关注/收藏成功");
        }else{
            return RestGenerator.genSuccessResult(null,"取消关注/收藏");
        }
    }

    @ApiOperation(value = "获取关注列表")
    @RequestMapping(value="/queryFollowUser", method= RequestMethod.POST)
    public RestResult queryFollowUser(@RequestBody Map<String, String> params, HttpServletRequest request){
        final int pageNo = Integer.parseInt(params.get("pageNo"));
        final int pageSize = Integer.parseInt(params.get("pageSize"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        List<Map> follows = userService.getFollowUsers(user, pageNo, pageSize);
        return RestGenerator.genSuccessResult(follows, "获取关注成功");
    }
    @ApiOperation(value = "获取收藏列表")
    @RequestMapping(value="/queryFollowDiary", method= RequestMethod.POST)
    public RestResult queryFollowDiary(@RequestBody Map<String, String> params, HttpServletRequest request){
        final int pageNo = Integer.parseInt(params.get("pageNo"));
        final int pageSize = Integer.parseInt(params.get("pageSize"));

        final User user = tokenUtil.getUserIdFromHttpReq(request);
        List<Map> diaries = userService.getCollectDiary(user, pageNo, pageSize);
        return RestGenerator.genSuccessResult(diaries, "获取收藏成功");
    }

}
