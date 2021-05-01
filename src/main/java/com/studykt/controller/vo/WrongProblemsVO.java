package com.studykt.controller.vo;

import java.util.List;

public class WrongProblemsVO {

    private String userId;

    private List<String> wrongIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getWrongIds() {
        return wrongIds;
    }

    public void setWrongIds(List<String> wrongIds) {
        this.wrongIds = wrongIds;
    }
}
