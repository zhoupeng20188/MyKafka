package com.zp;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        // zk地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.129.149:9092");
        // ack级别
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        //1.3、批次大小，一次达到多大的消息开始发送，16384代表16k。
//        props.put("batch.size", 16384);
//        //1.4、控制消息发送延时行为的，该参数默认值是0。表示消息需要被立即发送，无须关系batch是否被填满。
//        props.put("linger.ms", 10);
//        //1.5、RecordAccumulator缓冲区大小，设置的是最大能存多大内存的消息。如存放batch的消息，所以必须大于batch.size。默认33554432代表32兆。
//        props.put("buffer.memory", 33554432);

        // key,value的序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("topic001", "test--" + i));
        }

        producer.close();
    }
}
