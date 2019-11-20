package org.jboss.pnc.kafkastore.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.pnc.kafkastore.dto.KafkaMessageDTO;
import org.jboss.pnc.kafkastore.model.BuildStageRecord;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class BuildStageRecordMapper {

    ObjectMapper mapper = new ObjectMapper();

    public BuildStageRecord mapKafkaMsgToBuildStageRecord(String jsonString) throws BuildStageRecordMapperException {

        try {
            KafkaMessageDTO kafkaMessageDTO =  mapper.readValue(jsonString, KafkaMessageDTO.class);
            BuildStageRecord buildStageRecord = new BuildStageRecord();
            buildStageRecord.setDuration(kafkaMessageDTO.getOperationTook());
            buildStageRecord.setBuildStage(kafkaMessageDTO.getMdc().getProcessStageName());
            buildStageRecord.setBuildId(kafkaMessageDTO.getMdc().getProcessContext());
            return buildStageRecord;

        } catch(IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

    public String toJsonString(KafkaMessageDTO kafkaMessageDTO) throws BuildStageRecordMapperException {

        try {
            return mapper.writeValueAsString(kafkaMessageDTO);
        } catch(IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

}
