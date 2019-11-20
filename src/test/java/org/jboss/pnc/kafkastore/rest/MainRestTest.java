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
package org.jboss.pnc.kafkastore.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jboss.pnc.kafkastore.model.BuildStageRecord;

import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class MainRestTest {

    private String buildConfigId = "8";
    private String buildId = "20";
    private int firstBuildIdTimes = 10;

    private String secondBuildConfigId = buildConfigId;
    private String secondBuildId = "123";
    private int secondBuildIdTimes = 25;

    private String thirdBuildConfigId = "10";
    private String thirdBuildId = "999";
    private int thirdBuildIdTimes = 8;

    @BeforeEach
    void setup() {
        for (int i = 0; i < firstBuildIdTimes; i++) {
            createBuildStageRecordWithBuildId(buildId, buildConfigId, "test", 123);
        }

        for (int i = 0; i < secondBuildIdTimes; i++) {
            createBuildStageRecordWithBuildId(secondBuildId, secondBuildConfigId, "test2", 456);
        }
        for (int i = 0; i < thirdBuildIdTimes; i++) {
            createBuildStageRecordWithBuildId(thirdBuildId, thirdBuildConfigId, "test3", 888);
        }
    }

    @AfterEach
    @Transactional
    void tearDown() {
        BuildStageRecord.deleteAll();
    }

    @Test
    void getBuildMetricsForBuildId() {
        Response response = given().when().get("/build/" + buildId).then().statusCode(200).contentType(ContentType.JSON)
                .extract().response();

        assertThat(response.jsonPath().getList("").size()).isEqualTo(firstBuildIdTimes);
    }

    @Test
    void getBuildMetricsForNonExistentBuildIdShouldBeEmpty() {

        Response response = given().when().get("/build/" + "idonotexist").then().statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        assertThat(response.jsonPath().getList("").size()).isEqualTo(0);
    }

    @Test
    void getBuildMetricsForBuildConfigId() {
        given().when().get("/build-config/" + buildConfigId).then().statusCode(200).contentType(ContentType.JSON)
                .body("$", hasKey(buildId)).body("$", hasKey(secondBuildId))
                .body(buildId + ".size()", is(firstBuildIdTimes))
                .body(secondBuildId + ".size()", is(secondBuildIdTimes));

    }

    @Transactional
    void createBuildStageRecordWithBuildId(String buildId, String buildConfigId, String buildStage, int duration) {
        BuildStageRecord a = new BuildStageRecord();
        a.setBuildId(buildId);
        a.setBuildConfigId(buildConfigId);
        a.setBuildStage(buildStage);
        a.setDuration(duration);
        a.persist();
    }
}