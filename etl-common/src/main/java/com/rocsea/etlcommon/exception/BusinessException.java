package com.rocsea.etlcommon.exception;

/**
 * 业务异常
 * @author rocsea
 */
public class BusinessException extends RuntimeException {

    private final int code;
    private final String message;

    /**
     * 构建 业务exception
     *
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super();
        this.code = 0;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}