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
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
@ToString
public class BuildStageRecord extends PanacheEntity {

    String buildStage;
    long duration;
    String buildId;
    String buildConfigId;

    public static List<BuildStageRecord> getForBuildId(String buildId) {
        return list("buildId", Sort.by("id").ascending(), buildId);
    }

    public static List<BuildStageRecord> getForBuildId(int buildId) {
        return getForBuildId(String.valueOf(buildId));
    }

    public static Map<String, List<BuildStageRecord>> getForBuildConfigId(String buildConfigId, int amount) {

        List<String> buildIds = Panache.getEntityManager()
                .createQuery("SELECT DISTINCT buildId " + "FROM BuildStageRecord WHERE buildConfigId = :buildConfigId "
                        + "ORDER BY buildId DESC", String.class)
                .setParameter("buildConfigId", buildConfigId).setMaxResults(amount).getResultList();

        Stream<BuildStageRecord> temp = find("buildId IN ?1", buildIds).stream();
        return temp.collect(Collectors.groupingBy(BuildStageRecord::getBuildId));
    }

    public static Map<String, List<BuildStageRecord>> getForBuildConfigId(int buildConfigId, int amount) {
        return getForBuildConfigId(String.valueOf(buildConfigId), amount);
    }
}
