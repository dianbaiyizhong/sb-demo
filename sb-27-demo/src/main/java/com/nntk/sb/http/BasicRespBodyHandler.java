package com.nntk.sb.http;

public interface BasicRespBodyHandler<T> {

    public boolean isBusinessSuccess(String bodyMessage);

    public int getCode(String bodyMessage);

    public String getMessage(String bodyMessage);

    public  T getData(String bodyMessage);

}
