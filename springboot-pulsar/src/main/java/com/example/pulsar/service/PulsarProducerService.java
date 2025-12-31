package com.example.pulsar.service;

import com.example.pulsar.model.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class PulsarProducerService {

    private final PulsarTemplate<Message> pulsarTemplate;

    public PulsarProducerService(PulsarTemplate<Message> pulsarTemplate) {
        this.pulsarTemplate = pulsarTemplate;
    }

    public void send(Message msg) throws PulsarClientException {
        pulsarTemplate.send("example-topic", msg);
    }

}
