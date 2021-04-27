package com.studykt.response;

public class CommonReturnType {

    private Integer status;

    private Object data;

    public static CommonReturnType create() {
        return CommonReturnType.create(200, null);
    }

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(200, result);
    }

    public static CommonReturnType create(Integer status, Object result) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return commonReturnType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
