package com.syy.hardware.util;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public Map<Object,Object> supperReturn(Object o){
        Map<Object, Object> result = new HashMap<>();
        result.put("code", 20000);
        result.put("data",o);
        return result;
    }

    public Map<Object,Object> errReturn(Object o){
        Map<Object, Object> result = new HashMap<>();
        result.put("code", 40000);
        result.put("data",o);
        return result;
    }
}
