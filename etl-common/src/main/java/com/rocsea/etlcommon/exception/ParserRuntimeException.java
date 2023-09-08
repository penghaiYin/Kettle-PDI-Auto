package com.rocsea.etlcommon.exception;

/**
 * 日期解析异常
 * @Author rocsea
 * @Date 2023年6月8日
 */
public class ParserRuntimeException extends RuntimeException {
    /**
     * 构造方法
     *
     * @param msg 错误消息
     */
    public ParserRuntimeException(String msg) {
        super(msg);
    }
}
