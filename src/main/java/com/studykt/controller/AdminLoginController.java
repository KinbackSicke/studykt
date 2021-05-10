package com.studykt.controller;

import com.studykt.controller.vo.AdminVO;
import com.studykt.entity.Admin;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.AdminService;
import com.studykt.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@Api(value = "Api for admin login", tags = {"管理员登录，退出登录"})
@RequestMapping("/adminLogin")
@CrossOrigin(allowCredentials="true", allowedHeaders="*")
public class AdminLoginController extends BaseController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "admin login", notes = "管理员登录信息api")
    @PostMapping(value = "/login", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType login(@RequestParam("username") String username,
                                  @RequestParam("password") String password)
            throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String encryptedPassword = MD5Utils.encodeByMD5(password);
        Admin target = adminService.selectById(username);
        if (target == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "管理员不存在");
        } else if (!StringUtils.equals(encryptedPassword, target.getPassword())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户名或密码错误");
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(target, adminVO);
        adminVO.setPassword("");
        String uniqueToken = UUID.randomUUID().toString();
        adminVO.setUniqueToken(uniqueToken);
        redis.set(ADMIN_REDIS_SESSION + ":" + adminVO.getUsername(), uniqueToken, BASE_REDIS_TIMEOUT * 24);
        return CommonReturnType.create(adminVO);
    }

    @ApiOperation(value = "admin logout", notes = "管理员登录信息api")
    @ApiImplicitParam(name = "username", value = "管理员id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/logout")
    public CommonReturnType logout(String username) throws BusinessException {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "退出登录失败");
        }
        redis.del(ADMIN_REDIS_SESSION + ":" + username);
        return CommonReturnType.create("退出登录成功！");
    }

}
