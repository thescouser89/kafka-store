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
package org.jboss.pnc.kafkastore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
@ToString
@Table(
        indexes = { @Index(name = "idx_build_ids", columnList = "buildid"),
                @Index(name = "idx_lastupdate_time", columnList = "lastupdatetime") })
public class BuildStageRecord extends PanacheEntity {

    String buildStage;
    long duration;
    String buildId;

    String processContextVariant;

    Instant timestamp;

    @UpdateTimestamp
    Instant lastUpdateTime;

    public static List<BuildStageRecord> getForBuildId(String buildId) {
        return list("buildId", Sort.by("timestamp").ascending(), buildId);
    }

    public static List<BuildStageRecord> getForBuildId(int buildId) {
        return getForBuildId(String.valueOf(buildId));
    }

    public static List<BuildStageRecord> findNewerThan(Instant lastUpdate) {
        return find(
                "lastUpdateTime > :lastUpdate",
                Sort.by("lastUpdateTime").ascending(),
                Parameters.with("lastUpdate", lastUpdate)).list();
    }
}
