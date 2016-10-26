package com.jmessenger.util;

import java.util.UUID;

public class Generator {

    public static String channel() {
        return String.format("%s_%s", Constant.CHANNEL_PREFIX, UUID.randomUUID().toString());
    }
}
