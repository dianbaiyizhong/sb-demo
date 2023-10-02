package com.nntk.sb.rest;

import lombok.Data;

@Data
public class HttpPlusResponse {

    private int httpStatus;
    private String body;
}
