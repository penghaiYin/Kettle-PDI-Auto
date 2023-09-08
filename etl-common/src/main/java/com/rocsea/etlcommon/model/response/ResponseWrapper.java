package com.rocsea.etlcommon.model.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * 拟合ant design pro 亲戚返回
 *
 * @param <T> 包装泛型
 * @author rocsea
 **/
public class ResponseWrapper<T> {
    @ApiModelProperty("代码")
    private Integer code;
    @ApiModelProperty("是否成功")
    private Boolean success = true;
    @ApiModelProperty("错误信息")
    private String errorMessage;
    @ApiModelProperty("数据")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据泛型
     * @return 响应封装
     */
    public static <T> ResponseWrapper<T> success(T data) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setCode(0);
        responseWrapper.setData(data);
        responseWrapper.setSuccess(true);
        return responseWrapper;
    }

    /**
     * 失败
     *
     * @param code         代码
     * @param errorMessage 错误信息
     * @return 响应封装
     */
    public static ResponseWrapper fail(int code, String errorMessage) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(code);
        responseWrapper.setErrorMessage(errorMessage);
        return responseWrapper;
    }
}
