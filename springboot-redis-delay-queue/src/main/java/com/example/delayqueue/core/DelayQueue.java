package com.example.delayqueue.core;

import java.util.List;

public interface DelayQueue {

    boolean push(Message message);

    List<Message> pull();

    boolean remove(Message message);

}
