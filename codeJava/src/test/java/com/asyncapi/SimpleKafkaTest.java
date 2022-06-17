package com.asyncapi;

  
import com.asyncapi.model.ProdutoPayload;
  
  
import com.asyncapi.model.ProdutoPayload;
  
import com.asyncapi.service.PublisherService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Example of tests for kafka based on spring-kafka-test library
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleKafkaTest {
     
    private static final String PUB_PRODUTO_ALTERADO_SUBSCRIBE_TOPIC = "produto-alterado";
     
    private static final String SUB_PRODUTO_ALTERADO_PUBLISH_TOPIC = "produto-alterado";
     
    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 1, PUB_PRODUTO_ALTERADO_SUBSCRIBE_TOPIC);

    private static EmbeddedKafkaBroker embeddedKafkaBroker = embeddedKafka.getEmbeddedKafka();

    @DynamicPropertySource
    public static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", embeddedKafkaBroker::getBrokersAsString);
    }

    
    @Autowired
    private PublisherService publisherService;
       
    Consumer<Integer, ProdutoPayload> consumerProdutoAlterado;
       
    Producer<Integer, Object> producer;
    
    @Before
    public void init() {
        
        Map<String, Object> consumerConfigs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "true", embeddedKafkaBroker));
        consumerConfigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
           
        consumerProdutoAlterado = new DefaultKafkaConsumerFactory<>(consumerConfigs, new IntegerDeserializer(), new JsonDeserializer<>(ProdutoPayload.class)).createConsumer();
        consumerProdutoAlterado.subscribe(Collections.singleton(PUB_PRODUTO_ALTERADO_SUBSCRIBE_TOPIC));
        consumerProdutoAlterado.poll(Duration.ZERO);
           
        Map<String, Object> producerConfigs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(producerConfigs, new IntegerSerializer(), new JsonSerializer()).createProducer();
        
    }
     
    @Test
    public void pubProdutoAlteradoProducerTest() {  
        ProdutoPayload payload = new ProdutoPayload();
        Integer key = 1;

        KafkaTestUtils.getRecords(consumerProdutoAlterado);

        publisherService.pubProdutoAlterado(key, payload);

        ConsumerRecord<Integer, ProdutoPayload> singleRecord = KafkaTestUtils.getSingleRecord(consumerProdutoAlterado, PUB_PRODUTO_ALTERADO_SUBSCRIBE_TOPIC);

        assertEquals("Key is wrong", key, singleRecord.key());
    }
         
    @Test
    public void subProdutoAlteradoConsumerTest() throws InterruptedException {
        Integer key = 1;  
        ProdutoPayload payload = new ProdutoPayload();

        ProducerRecord<Integer, Object> producerRecord = new ProducerRecord<>(SUB_PRODUTO_ALTERADO_PUBLISH_TOPIC, key, payload);
        producer.send(producerRecord);
        producer.flush();
        Thread.sleep(1_000);
    }
        
    
}
