package com.nntk.sb.restplus.returntype;

import com.nntk.sb.restplus.RespBodyHandleRule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPlusVoid {
    private int httpStatus;
    private Throwable throwable;
    private RespBodyHandleRule respBodyHandleRule;
}
