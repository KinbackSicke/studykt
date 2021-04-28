package com.studykt.controller;

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
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
        if (StringUtils.isBlank(openid)) {
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
        if (studyRecord == null || StringUtils.isBlank(studyRecord.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        studyRecord.setId(sid.nextShort());
        userService.addStudyRecord(studyRecord);
        long time = studyRecord.getEndDate().getTime();
        // 更新连续学习的时间
        updateDaysCount(time, studyRecord.getUserId());
        // redis中设置最后一次学习时间
        redis.set(USER_LAST_STUDY + ":" + studyRecord.getUserId(),
                String.valueOf(time), BASE_REDIS_TIMEOUT * 24 * 30);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "user total study time", notes = "用户学习总时间api")
    @ApiImplicitParam(name="userId", value="用户的id", required=true, dataType="string", paramType="query")
    @PostMapping("/studyInfo")
    public CommonReturnType studyInfo(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        // 获取总的学习的时间
        Map<String, Integer> map = new HashMap<>();
        Integer sum = userService.selectTotalStudyTimeById(userId);
        map.put("total", sum);
        // 获取连续学习时间
        int constDays = getDaysCount(userId);
        map.put("DaysCount", constDays);
        return CommonReturnType.create(map);
    }

    private void updateDaysCount(long now, String userId) {
        // 获取最后一次学习的日期
        String lastDateStr = redis.get(USER_LAST_STUDY + ":" + userId);
        // 如果最后一次学习的日期不存在，设置连续学习天数=0（还未学习过）
        if (StringUtils.isBlank(lastDateStr)) {
            redis.set(USER_CONSTANT_STUDY + ":" + userId,
                    String.valueOf(0), BASE_REDIS_TIMEOUT * 24 * 30);
            return;
        }
        int days = getDaysCount(userId);
        if (days == 0) {
            days = 1;
        }
        // 存在则计算上一次学习时间和当前学习时的天数差
        long lastDateStamp = Long.parseLong(lastDateStr);
        int dayDiff = daysDiff(new Date(lastDateStamp), new Date(now));
        if (dayDiff == 1) {
            // 天数等于1，连续学习天数加1
            days += 1;
        } else if (dayDiff > 1) {
            // 天数大于1，重置连续学习天数为0
            days = 0;
        }
        redis.set(USER_CONSTANT_STUDY + ":" + userId,
                String.valueOf(days), BASE_REDIS_TIMEOUT * 24 * 30);
    }

    private int getDaysCount(String userId) {
        // 获取redis中用户当前连续学习天数
        String constantDaysStr = redis.get(USER_CONSTANT_STUDY + ":" + userId);
        // 如果存在，返回该值，否则返回0
        if (StringUtils.isNotBlank(constantDaysStr)) {
            return Integer.parseInt(constantDaysStr);
        }
        return 0;
    }

}
