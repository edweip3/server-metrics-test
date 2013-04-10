package com.test.metrics.resource;

import com.google.inject.Inject;
import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.jersey.JerseyProtostuff;
import com.test.metrics.model.ClientEventUploadRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource that handles POST and GET requests for metrics data.
 */
@Path("/v2/events")
public class ClientMetricsResourceV2
{
    private static final Logger log = LoggerFactory.getLogger(ClientMetricsResourceV2.class);

    @Inject
    DatabaseAccess dbAccess;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, JerseyProtostuff.APPLICATION_PROTOBUF})
    public Response handlePost(ClientEventUploadRequest upload)
    {
        log.trace("handlePost(ClientEventUploadRequest)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, JerseyProtostuff.APPLICATION_PROTOBUF})
    public Response handleGet(@PathParam("id") String id)
    {
        log.trace("handleGet(String)");
        /**
         * TODO: implement me
         */
        throw new UnsupportedOperationException("implement me");
    }
}
