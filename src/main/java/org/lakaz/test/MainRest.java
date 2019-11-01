package org.lakaz.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class MainRest {

    @GET
    public long getCount() {
        return Message.count();
    }
}
