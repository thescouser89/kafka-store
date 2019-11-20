package org.jboss.pnc.kafkastore.model;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@QuarkusTest
class BuildStageRecordTest {

    @Test
    void getForBuildId() {

        String buildId = "5";
        for (int i = 0; i < 15; i++) {
            createBuildStageRecordWithBuildId(buildId, "1", "test", 123);
        }
        assertThat(BuildStageRecord.getForBuildId("5")).hasSize(15);
    }

    @Test
    void testGetForBuildConfigId() {
        String buildId = "5";
        String buildIdSecond = "6";
        String buildIdThird = "7";

        String buildConfigId = "1";
        String buildConfigIdSecond = "2";

        for (int i = 0; i < 11; i++) {
            createBuildStageRecordWithBuildId(buildId, buildConfigId, "test " + new Date(), 123);
        }

        for (int i = 0; i < 7; i++) {
            createBuildStageRecordWithBuildId(buildIdSecond, buildConfigId, "test2 " + new Date(), 456);
        }

        for (int i = 0; i < 8; i++) {
            createBuildStageRecordWithBuildId(buildIdThird, buildConfigIdSecond, "test3 " + new Date(), 789);
        }

        Map<String, List<BuildStageRecord>> result = BuildStageRecord.getForBuildConfigId(buildConfigId, 100);
        assertThat(result.keySet())
                .as("check if result contains proper build id size")
                .hasSize(2);
        Assertions.assertThat(result.get(buildId))
                .as("check if each build id has the proper number of stages")
                .hasSize(11);
        Assertions.assertThat(result.get(buildIdSecond))
                .as("check if each build id has the proper number of stages")
                .hasSize(7);

        Map<String, List<BuildStageRecord>> resultOne = BuildStageRecord.getForBuildConfigId(buildConfigId, 1);
        assertThat(resultOne.keySet())
                .as("check if result returns only 1 build id")
                .hasSize(1);
        Assertions.assertThat(resultOne.get(buildIdSecond))
                .as("check if each build id has the proper number of stages")
                .isNotNull()
                .hasSize(7);

        Map<String, List<BuildStageRecord>> buildConfigSecondResult = BuildStageRecord.getForBuildConfigId(buildConfigIdSecond, 5);

        assertThat(buildConfigSecondResult.keySet())
                .as("check if result contains proper build id size")
                .hasSize(1);
        Assertions.assertThat(buildConfigSecondResult.get(buildIdThird))
                .as("check if each build id has the proper number of stages")
                .hasSize(8);
    }

    // *****************************************************************************************************************
    // Helper methods
    // *****************************************************************************************************************
    @Transactional
    void createBuildStageRecordWithBuildId(String buildId, String buildConfigId, String buildStage, int duration) {
        BuildStageRecord a = new BuildStageRecord();
        a.buildId = buildId;
        a.buildConfigId = buildConfigId;
        a.buildStage = buildStage;
        a.duration = duration;
        a.persist();
    }

    @Transactional
    @AfterEach
    void cleanup() {
        BuildStageRecord.deleteAll();
    }
}