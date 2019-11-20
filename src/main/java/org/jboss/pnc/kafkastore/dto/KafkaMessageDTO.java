package org.jboss.pnc.kafkastore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaMessageDTO {

    MDC mdc;

    /**
     * Value is in milliseconds
     */
    long operationTook;
}
