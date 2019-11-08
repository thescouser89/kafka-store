package org.lakaz.test.dto;

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
