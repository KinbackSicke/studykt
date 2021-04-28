package com.studykt.controller;


import com.mysql.cj.util.StringUtils;
import com.studykt.entity.StudyRecord;
import com.studykt.entity.User;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.UserService;
import com.studykt.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


@RestController
@Api(value = "Api for user operations", tags = {"用户相关操作"})
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "user information", notes = "获取用户信息api")
    @ApiImplicitParam(name="openid", value="用户的openid", required=true, dataType="string", paramType="query")
    @PostMapping("/userQuery")
    public CommonReturnType userQuery(String openid) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isNullOrEmpty(openid)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "获取用户信息失败");
        }
        User user = userService.selectByOpenid(openid);
        if (user == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "获取用户信息失败");
        }
        return CommonReturnType.create(user);
    }

    @ApiOperation(value = "add user study record", notes = "用户学习记录api")
    @PostMapping("/addStudyRecord")
    public CommonReturnType addStudyRecord(@RequestBody StudyRecord studyRecord) throws BusinessException {
        if (studyRecord == null || org.apache.commons.lang3.StringUtils.isBlank(studyRecord.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        studyRecord.setId(sid.nextShort());
        userService.addStudyRecord(studyRecord);
        return CommonReturnType.create();
    }



}
