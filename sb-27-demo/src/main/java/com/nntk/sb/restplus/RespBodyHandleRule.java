package com.nntk.sb.restplus;

import lombok.Getter;

@Getter
public abstract class RespBodyHandleRule {

    public void setHttpBody(String httpBody) {
        this.httpBody = httpBody;
    }

    private String httpBody;

    public abstract void init(String httpBody);
    public abstract int getCode();

    public abstract boolean isBusinessSuccess();


    public abstract String getMessage();

    public abstract String getData();

}
