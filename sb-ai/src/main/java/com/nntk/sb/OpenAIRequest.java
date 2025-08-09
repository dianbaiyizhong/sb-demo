package com.nntk.sb;

import lombok.Data;

@Data
public class OpenAIRequest {
    private String model;
    private Object messages;
}
