package com.rocsea.etlcommon.exception;

/**
 * @Author RocSea
 * @Date 2022/12/7
 */
public class SqlRuntimeException extends RuntimeException {

    /**
     * 构造方法
     *
     * @param ex 异常
     */
    public SqlRuntimeException(Throwable ex) {
        super(ex);
    }
}
