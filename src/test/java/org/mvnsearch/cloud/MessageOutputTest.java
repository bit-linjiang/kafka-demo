package org.mvnsearch.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * message output test
 *
 * @author linux_china
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MessageOutputTest {
    @Autowired
    private Source source;

    @Autowired
    private Sink sink;

    @Test
    public void testSend() {
        for (int i = 1; i < 10000; i++) {

            Message<String> message = MessageBuilder.withPayload(
                    "{id:" + i + ",key:source,time:" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "}")
                    .setHeader(KafkaHeaders.MESSAGE_KEY, "1".getBytes())
                    .build();
            source.output().send(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSend2() {
        for (int i = 1; i < 20000; i++) {

            Message<String> message = MessageBuilder.withPayload(
                    "{id:" + i + ",key:sink,time:" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "}")
                    .setHeader(KafkaHeaders.MESSAGE_KEY, "1".getBytes())
                    .build();
            sink.input().send(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
