package com.test.metrics.resource;

import com.google.inject.Inject;
import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.jersey.JerseyProtostuff;
import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource that handles POST and GET requests for metrics data.
 */
@Path("/v1/events")
public class ClientMetricsResourceV1
{
    private static final Logger log = LoggerFactory.getLogger(ClientMetricsResourceV1.class);

    @Inject
    DatabaseAccess dbAccess;

    @POST
    @Consumes(JerseyProtostuff.APPLICATION_PROTOBUF)
    public Response handlePostProtobuf(InputStream is)
    {
        log.trace("handlePostProtobuf(InputStream)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostJson(InputStream is)
    {
        log.trace("handlePostJson(InputStream)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGetJson(@PathParam("id") String id)
    {
        log.trace("handleGetJson(String)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }

    @GET
    @Path("{id}")
    @Produces(JerseyProtostuff.APPLICATION_PROTOBUF)
    public Response handleGetProtobuf(@PathParam("id") String id)
    {
        log.trace("handleGetProtobuf(String)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }
}
