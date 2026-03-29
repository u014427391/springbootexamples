package com.example.ai.util;

import java.util.UUID;

public class ConversationIdUtil {

    private static final String PREFIX = "chat";
    private static final String SEP = ":";

    public static String generate(String userId) {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        return PREFIX + userId + ":" + uuid;
    }

    public static String generateGuest() {
        return PREFIX + SEP + "guest" + SEP + UUID.randomUUID();
    }
}