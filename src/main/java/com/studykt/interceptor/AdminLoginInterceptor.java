package com.studykt.interceptor;

import com.studykt.response.CommonReturnType;
import com.studykt.utils.JsonUtils;
import com.studykt.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AdminLoginInterceptor implements HandlerInterceptor {

    public static final String ADMIN_REDIS_SESSION = "admin-redis-session";

    @Autowired
    public RedisOperator redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminId = request.getHeader("adminId");
        String adminToken = request.getHeader("adminToken");
        if (StringUtils.isNotBlank(adminId) && StringUtils.isNotBlank(adminToken)) {
            System.out.println("请求拦截...");
            String uniqueToken = redis.get(ADMIN_REDIS_SESSION + ":" + adminId);
            if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
                System.out.println("请重新登录...");
                returnErrorResponse(response, CommonReturnType.create(501,"请重新登录..."));
                return false;
            } else {
                if (!uniqueToken.equals(adminToken)) {
                    System.out.println("账号已在别处登录...");
                    returnErrorResponse(response, CommonReturnType.create(501,"请重新登录..."));
                    return false;
                }
            }
        } else {
            returnErrorResponse(response, CommonReturnType.create(501,"请重新登录..."));
            return false;
        }
        System.out.println("通过拦截器...");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, CommonReturnType result) throws IOException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
