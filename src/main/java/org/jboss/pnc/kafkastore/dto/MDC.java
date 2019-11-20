package org.jboss.pnc.kafkastore.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDC {

    String processContext;
    String requestContext;
    int duration;
    String userId;

    String processStageName;
}
