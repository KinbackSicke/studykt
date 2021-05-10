package com.studykt.controller;

import com.studykt.entity.Problem;
import com.studykt.entity.ProblemOption;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class BaseController {

    @Autowired
    public RedisOperator redis;

    @Autowired
    public Sid sid;
    // 用户登录redis
    public static final String USER_REDIS_SESSION = "user-redis-session";
    // 管理员登录redis
    public static final String ADMIN_REDIS_SESSION = "admin-redis-session";
    // 用户历史记录redis
    public static final String USER_COURSE_HISTORY = "user-course-history";
    // 用户最后一次学习时间
    public static final String USER_LAST_STUDY = "user-last-study";
    // 用户连续学习天数
    public static final String USER_CONSTANT_STUDY = "user-constant-study";
    // 用户错题记录
    public static final String USER_WRONG_PROBLEMS = "user-wrong-problem";
    // redis默认过期时间1小时
    public static final long BASE_REDIS_TIMEOUT = 3600;
    // 微信小程序APP ID
    public static final String APP_ID = "wxfd4bfc16294b0ddd";
    // 微信小程序APP SECRET
    public static final String APP_SECRET = "d52c97f48ce9cc39e3722a58a9581f56";
    // 微信登录请求接口
    public static final String API_WEIXIN_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // 分页默认每页大小
    public static final Integer DEFAULT_PAGE_SIZE = 8;

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handlerException(HttpServletRequest request, Exception e) {
        Map<String, Object> responseData = new HashMap<>();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            responseData.put("errCode", businessException.getErrorCode());
            responseData.put("errMsg", businessException.getErrorMsg());
        }  else if (e instanceof MethodArgumentTypeMismatchException) {
            responseData.put("errCode", BusinessError.PARAMETER_MISMATCH_ERROR.getErrorCode());
            responseData.put("errMsg", BusinessError.PARAMETER_MISMATCH_ERROR.getErrorMsg());
        } else {
            responseData.put("errCode", BusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg", BusinessError.UNKNOWN_ERROR.getErrorMsg());
            e.printStackTrace();
        }
        return CommonReturnType.create(500, responseData);
    }

    public String formatDate(String pattern, Date date) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public String formatDate(String pattern, long timeStamp) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(pattern).format(timeStamp);
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public int daysDiff(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++) {
                if(i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                }
                else {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else {
            //System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2 - day1;
        }
    }

}
