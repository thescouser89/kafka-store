package org.lakaz.test.rest;

import org.lakaz.test.model.BuildStageRecord;

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
    public Map<String, List<BuildStageRecord>> getBuildMetricsForBuildConfigId(@PathParam("build-config-id") String buildConfigId, @QueryParam("size") Integer size) {

        if (size == null) {
            size = DEFAULT_SIZE_BUILD_ID;
        }

        return BuildStageRecord.getForBuildConfigId(buildConfigId, size);
    }
}
