package com.nntk.sb.restplus;

import java.util.Map;

public abstract class AbsHttpFactory {

    public abstract HttpPlusResponse post(String url, Map<String, String> header, String body);

    public abstract HttpPlusResponse get(String url, Map<String, String> header);

}
