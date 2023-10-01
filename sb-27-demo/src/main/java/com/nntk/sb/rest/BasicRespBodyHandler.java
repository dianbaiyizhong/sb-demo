package com.nntk.sb.rest;

public abstract class BasicRespBodyHandler {

    public void setHttpBody(String httpBody) {
        this.httpBody = httpBody;
    }

    protected String httpBody;

    public abstract boolean isBusinessSuccess();

    public abstract int getCode();

    public abstract String getMessage();

    public abstract String getData();

}
