package org.lakaz.test.kafka;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.lakaz.test.mapper.BuildStageRecordMapper;
import org.lakaz.test.dto.BuildStageRecordDTO;
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

                    BuildStageRecordDTO buildStageRecord = factory.manufacturePojo(BuildStageRecordDTO.class);
                    String data = mapper.toJsonString(buildStageRecord);

                    log.debug("Sending: {}", data);
                    return data;
                });
    }

}
