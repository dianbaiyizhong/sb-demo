package com.nntk.sb.api.local;

import lombok.Data;

@Data
public class LocalBodyEntity<T> {
    private int code;
    private String message;
    private T data;
}
