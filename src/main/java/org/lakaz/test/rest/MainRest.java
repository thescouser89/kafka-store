package org.lakaz.test.rest;

import org.lakaz.test.Message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/test")
public class MainRest {

    @GET
    public long getCount() {
        return Message.count();
    }

    @GET
    @Path("/build/{build-id}")
    public List<Message> getBuildMetricsForBuildId(@PathParam("build-id") String buildId) {
        return null;
    }

    @GET
    @Path("/build-config/{build-config-id}")
    public List<Message> getBuildMetricsForBuildConfigId(@PathParam("build-config-id") String buildConfigId, @QueryParam("size") Integer size) {
        return null;
    }
}
