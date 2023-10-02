package com.nntk.sb.rest;

import java.util.Map;

public abstract class HttpFactory {

    public abstract HttpPlusResponse post(String url, Map<String, String> header, String body);

    public abstract HttpPlusResponse get(String url, Map<String, String> header);

}
