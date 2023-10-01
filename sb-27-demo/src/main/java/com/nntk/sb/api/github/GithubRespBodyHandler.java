package com.nntk.sb.api.github;


import com.nntk.sb.rest.BasicRespBodyHandler;

public class GithubRespBodyHandler extends BasicRespBodyHandler {

    @Override
    public boolean isBusinessSuccess() {
        return true;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getData() {
        return httpBody;
    }

}
