package org.lakaz.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class Consumer {

    ObjectMapper mapper = new ObjectMapper();

    @Incoming("duration")
    public void consume(String test) throws Exception {
        Message message = mapper.readValue(test, Message.class);
        System.out.println(message.message);
        CompletableFuture.runAsync(() -> store(message)).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    @Transactional
    public void store(Message message) {
        message.persist();
    }
}
