package com.zp;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class CallbackProducerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        // zk地址 kafka的配置文件server.properties中advertised.listeners需要配置，不然api无法发送
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.129.149:9092");
        // ack级别
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // key,value的序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("topic001", "testcallback--" + i), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(metadata.partition()  +" " + metadata.offset() +" " + metadata.timestamp());
                }
            });
        }

        producer.close();
    }
}
