package com.studykt.service.Impl;

import com.studykt.entity.Message;
import com.studykt.mapper.MessageMapper;
import com.studykt.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Message getMessageById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Message> getAllMessage() {
        return messageMapper.selectAllOrderByCreateDate();
    }

    @Override
    public int addMessage(Message message) {
        if (message == null) {
            return -1;
        }
        return messageMapper.insert(message);
    }

    @Override
    public int deleteMessageById(String id) {
        if (StringUtils.isBlank(id)) {
            return -1;
        }
        return messageMapper.deleteByPrimaryKey(id);
    }
}
