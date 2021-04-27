package com.studykt.error;

public enum BusinessError implements CommonError {

    UNKNOWN_ERROR(99999, "未知错误"),
    /* 100XX 参数类型错误码 */
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    PARAMETER_MISMATCH_ERROR(10002, "参数不合法");

    private int errCode;

    private String errMsg;

    private BusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errMsg = errorMsg;
        return this;
    }
}
