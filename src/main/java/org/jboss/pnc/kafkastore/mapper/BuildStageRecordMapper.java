/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2019 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            KafkaMessageDTO kafkaMessageDTO = mapper.readValue(jsonString, KafkaMessageDTO.class);
            BuildStageRecord buildStageRecord = new BuildStageRecord();
            buildStageRecord.setDuration(kafkaMessageDTO.getOperationTook());
            buildStageRecord.setBuildStage(kafkaMessageDTO.getMdc().getProcessStageName());
            buildStageRecord.setBuildId(kafkaMessageDTO.getMdc().getProcessContext());
            return buildStageRecord;

        } catch (IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

    public String toJsonString(KafkaMessageDTO kafkaMessageDTO) throws BuildStageRecordMapperException {

        try {
            return mapper.writeValueAsString(kafkaMessageDTO);
        } catch (IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

}
