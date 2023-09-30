package com.nntk.sb.api.github;

import lombok.Data;

@Data
public class GithubBodyEntity<T> {
    private int code;
    private String message;
    private T data;
}
