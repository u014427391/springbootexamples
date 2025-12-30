package com.example.pulsar.service;

import com.example.pulsar.model.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class PulsarProducerService {

    private final PulsarTemplate<String> pulsarTemplate;

    public PulsarProducerService(PulsarTemplate<String> pulsarTemplate) {
        this.pulsarTemplate = pulsarTemplate;
    }

    public void send(String msg) throws PulsarClientException {
        pulsarTemplate.send("demo-topic", msg);
    }

}
