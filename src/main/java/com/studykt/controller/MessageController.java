package com.studykt.controller;


import com.studykt.entity.Course;
import com.studykt.entity.Message;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@Api(value = "api for message", tags = {"消息相关接口"})
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    // 获取消息
    @ApiOperation(value = "get a message by id", notes = "获取消息api")
    @ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getMessage")
    public CommonReturnType getMessage(String id) throws BusinessException {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "消息不存在");
        }
        Message message = messageService.getMessageById(id);
        if (message == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "消息不存在");
        }
        return CommonReturnType.create(message);
    }

    // 添加消息
    @ApiOperation(value = "get all messages", notes = "添加消息api")
    @PostMapping("/allMessage")
    public CommonReturnType getAllMessage() throws BusinessException {
        List<Message> list = messageService.getAllMessage();
        return CommonReturnType.create(list);
    }

    // 添加消息
    @ApiOperation(value = "add message", notes = "添加消息api")
    @PostMapping("/addMessage")
    public CommonReturnType addCourse(@RequestBody Message message) throws BusinessException {
        if (message == null) {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
        message.setId(sid.nextShort());
        message.setCreateDate(new Date());
        messageService.addMessage(message);
        return CommonReturnType.create("添加成功！");
    }

    // 删除消息
    @ApiOperation(value = "delete message", notes = "添加消息api")
    @ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/deleteMessage")
    public CommonReturnType deleteMessage(String id) throws BusinessException {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "消息不存在");
        }
        messageService.deleteMessageById(id);
        return CommonReturnType.create("删除成功");
    }




}
