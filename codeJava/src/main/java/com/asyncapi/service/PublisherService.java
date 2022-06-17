package com.asyncapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.asyncapi.model.ProdutoPayload;

@Service
public class PublisherService {

    @Autowired
    private KafkaTemplate<Integer, Object> kafkaTemplate;

    /**
     * Esse topico representa produto criado
     */
    public void pubProdutoAlterado(Integer key, ProdutoPayload produtoPayload) {
        Message<ProdutoPayload> message = MessageBuilder.withPayload(produtoPayload)
                .setHeader(KafkaHeaders.TOPIC, "produto-alterado")
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .build();
        kafkaTemplate.send(message);
    }
}
