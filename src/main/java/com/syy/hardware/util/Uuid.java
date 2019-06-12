package com.syy.hardware.util;

import java.util.UUID;

public class Uuid {
    public String getUUid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
