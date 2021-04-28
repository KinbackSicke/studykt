package com.studykt.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.studykt.App;
import com.studykt.controller.vo.UserVO;
import com.studykt.entity.User;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.UserService;
import com.studykt.utils.JsonUtils;
import com.studykt.utils.MD5Utils;
import com.studykt.utils.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(value = "Api for weChat mini program login", tags = {"用户微信登录"})
@RequestMapping("wxApi")
public class WxLoginController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "user Login", notes = "用户微信登录接口")
    @ApiImplicitParam(name = "code", value = "code", required = true, dataType = "string", paramType = "query")
    @PostMapping("/login")
    public CommonReturnType login(String code, @RequestBody User user) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户code获取失败");
        }
        JSONObject result = getUserWXLoginInfo(code);
        if (result == null || !result.containsKey("openid")) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "获取用户信息失败");
        }
        // 获取openid
        String openid = result.getString("openid");
        // 把openid用MD5加密
        String encryptOpenid = MD5Utils.encodeByMD5(openid);
        user.setOpenid(encryptOpenid);
        user.setLastUpdateTime(new Date());
        if (userService.selectByOpenid(encryptOpenid) != null) {
            userService.updateUser(user);
        } else {
            userService.addUser(user);
        }
        UserVO userVO = convertFromDO2VO(user);
        BeanUtils.copyProperties(user, userVO);
        // 生成用户的唯一token
        String uniqueToken = UUID.randomUUID().toString();
        // 设置redis
        redis.set(USER_REDIS_SESSION + ":" + userVO.getOpenid(), uniqueToken, BASE_REDIS_TIMEOUT * 24 * 7);
        userVO.setUniqueToken(uniqueToken);
        return CommonReturnType.create(userVO);
    }

    @ApiOperation(value = "User logout", notes = "用户退出登录api")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/logout")
    public CommonReturnType logout(String userId) {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return CommonReturnType.create();
    }


    /**
     * 获取微信用户登录的唯一openid
     * @param code
     * @return
     */
    private JSONObject getUserWXLoginInfo(String code) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("appid", APP_ID);
        requestParams.put("secret", APP_SECRET);
        requestParams.put("js_code", code);
        requestParams.put("grant_type", "authorization_code");
        return JSONObject.parseObject(UrlUtils.doGet(API_WEIXIN_REQUEST_URL, requestParams));
    }

    private UserVO convertFromDO2VO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
