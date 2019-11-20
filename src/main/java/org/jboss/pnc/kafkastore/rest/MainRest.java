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

import org.jboss.pnc.kafkastore.model.BuildStageRecord;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/")
public class MainRest {

    public static final int DEFAULT_SIZE_BUILD_ID = 5;

    @GET
    public long getCount() {
        return BuildStageRecord.count();
    }

    @GET
    @Path("/build/{build-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BuildStageRecord> getBuildMetricsForBuildId(@PathParam("build-id") String buildId) {
        return BuildStageRecord.getForBuildId(buildId);
    }

    @GET
    @Path("/build-config/{build-config-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<BuildStageRecord>> getBuildMetricsForBuildConfigId(
            @PathParam("build-config-id") String buildConfigId, @QueryParam("size") Integer size) {

        if (size == null) {
            size = DEFAULT_SIZE_BUILD_ID;
        }

        return BuildStageRecord.getForBuildConfigId(buildConfigId, size);
    }
}
