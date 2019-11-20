package org.jboss.pnc.kafkastore.kafka;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.pnc.kafkastore.dto.KafkaMessageDTO;
import org.jboss.pnc.kafkastore.mapper.BuildStageRecordMapper;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Slf4j
public class KafkaMessageGenerator {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    BuildStageRecordMapper mapper;

    @Outgoing("duration")
    public Flowable<String> generate() {

        return Flowable.interval(200, TimeUnit.MILLISECONDS)
                .map(tick -> {

                    KafkaMessageDTO kafkaMessageDTO = factory.manufacturePojo(KafkaMessageDTO.class);
                    String data = mapper.toJsonString(kafkaMessageDTO);

                    log.debug("Sending: {}", data);
                    return data;
                });
    }

}
