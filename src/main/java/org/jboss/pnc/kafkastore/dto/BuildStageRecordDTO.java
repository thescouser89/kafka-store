package org.jboss.pnc.kafkastore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildStageRecordDTO {

    String buildStage;
    long duration;
    String buildId;
    String buildConfigId;
}
