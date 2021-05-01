package com.studykt.service;

import com.studykt.entity.Message;

import java.util.List;

public interface MessageService {

    Message getMessageById(String id);

    List<Message> getAllMessage();

    int addMessage(Message message);

    int deleteMessageById(String id);

}
