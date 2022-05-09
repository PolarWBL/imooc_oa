package com.ctgu.oa.service.exception;

/**
 * @author Boliang Weng
 */
public class BusinessException extends RuntimeException{
    private String code;
    private String message;
    public BusinessException(String code, String message){
        super(code + ":" + message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
