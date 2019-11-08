package org.lakaz.test.kafka;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.lakaz.test.model.BuildStageRecord;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class ConsumerTest {

    @Inject
    KafkaMessageGenerator kafkaMessageGenerator;


    @Test
    void consumeTest() throws Exception {
        // wait for some messages to be consumed
        Thread.sleep(3000);
        assertThat(BuildStageRecord.count()).isNotZero();
    }

}