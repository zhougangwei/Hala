package com.hala.bean;

/**
 * Created by kiddo on 2017/12/27.
 */

public class BaseBean<T>  {
    public int code;
    public String message;
    public T t;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
