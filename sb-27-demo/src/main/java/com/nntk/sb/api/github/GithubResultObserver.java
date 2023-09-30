package com.nntk.sb.api.github;

import com.nntk.sb.http.BasicRespObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一处理，可以用来搞一些日志输出等
 */
@Slf4j
public class GithubResultObserver extends BasicRespObserver {
    @Override
    public void callBusinessFail(int code, String messages) {
        log.info("callBusinessFail...");
    }

    @Override
    public void complete() {
        log.info("complete...");

    }

    @Override
    public void callHttpFail(int httpStatus, String message) {
        log.info("callHttpFail...");

    }

    @Override
    public void callUnknownException(Throwable throwable) {
        log.info("callUnknownException...");

    }

    @Override
    public void callHttpSuccess() {
        log.info("callHttpSuccess...");

    }

    @Override
    public void callBusinessSuccess() {
        log.info("callBusinessSuccess...");

    }
}
