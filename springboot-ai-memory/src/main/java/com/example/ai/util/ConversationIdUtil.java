package com.example.ai.util;

import java.util.UUID;

public class ConversationIdUtil {

    private static final String PREFIX = "chat";
    private static final String SEP = ":";

    public static String generate(String userId) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return PREFIX + SEP + "user" + SEP + userId + SEP + uuid;
    }

    public static String generateGuest() {
        return PREFIX + SEP + "guest" + SEP + UUID.randomUUID();
    }
}