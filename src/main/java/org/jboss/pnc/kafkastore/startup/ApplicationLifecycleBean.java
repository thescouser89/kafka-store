package org.jboss.pnc.kafkastore.startup;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
@Slf4j
public class ApplicationLifecycleBean {

    @ConfigProperty(name = "mp.messaging.incoming.duration.topic")
    String topic;

    @ConfigProperty(name = "mp.messaging.incoming.duration.bootstrap.servers")
    String server;


    @ConfigProperty(name = "mp.messaging.incoming.duration.ssl.truststore.location")
    String location;

    @ConfigProperty(name = "mp.messaging.incoming.duration.ssl.truststore.password")
    String password;

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting...");
        log.info("Topic: " + topic);
        log.info("Server: " + server);
        log.info("Location: " + location);
        log.info("Password: " + password);
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("The application is stopping...");
    }
}
