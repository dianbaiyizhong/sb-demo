package com.nntk.sb.restplus;

import lombok.Data;

@Data
public class HttpPlusResponse {
    private int httpStatus;
    private String body;
}
