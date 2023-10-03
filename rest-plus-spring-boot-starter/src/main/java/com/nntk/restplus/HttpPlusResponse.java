package com.nntk.restplus;

import lombok.Data;

@Data
public class HttpPlusResponse {
    private int httpStatus;
    private String body;
}
