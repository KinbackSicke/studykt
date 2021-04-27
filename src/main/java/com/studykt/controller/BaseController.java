package com.studykt.controller;

import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.utils.RedisOperator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {

    @Autowired
    public RedisOperator redis;

    @Autowired
    public Sid sid;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    public static final String USER_COURSE_HISTORY = "user-course-history";

    public static final long BASE_REDIS_TIMEOUT = 3600;

    public static final String APP_ID = "wxfd4bfc16294b0ddd";

    public static final String APP_SECRET = "d52c97f48ce9cc39e3722a58a9581f56";

    public static final String API_WEIXIN_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final Integer DEFAULT_PAGE_SIZE = 8;

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

}
