package org.lakaz.test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.lakaz.test.mapper.BuildStageRecordMapper;
import org.lakaz.test.model.BuildStageRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

/**
 * Consume from a Kafka topic, parse the data and store it in the database
 */
@ApplicationScoped
@Slf4j
public class Consumer {

    @Inject
    BuildStageRecordMapper mapper;

    /**
     * Main method to consume information from a Kafka topic into the database.
     *
     * @param jsonString
     * @throws Exception
     */
    @Incoming("duration")
    public void consume(String jsonString) throws Exception {

        log.debug("Consuming data! {}", jsonString);
        BuildStageRecord buildStageRecord = mapper.map(jsonString);

        // TODO: handle error better
        // do this because method running in an IO thread and we can only store a POJO in a worker thread
        CompletableFuture.runAsync(() -> store(buildStageRecord)).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    @Transactional
    void store(BuildStageRecord message) {
        message.persist();
    }
}
