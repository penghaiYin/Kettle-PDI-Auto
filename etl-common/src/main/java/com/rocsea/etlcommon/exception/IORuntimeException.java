package com.rocsea.etlcommon.exception;

/**
 * IO异常
 * @Author rocsea
 * @Date 2023年6月8日
 */
public class IORuntimeException extends RuntimeException {
    /**
     * 构造方法
     *
     * @param msg 错误消息
     */
    public IORuntimeException(String msg) {
        super(msg);
    }
}
