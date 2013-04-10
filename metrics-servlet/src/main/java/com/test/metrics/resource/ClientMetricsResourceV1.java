package com.test.metrics.resource;

import com.dyuproject.protostuff.JsonIOUtil;
import com.dyuproject.protostuff.JsonInputException;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.google.inject.Inject;
import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.jersey.JerseyProtostuff;
import com.test.metrics.model.ClientEventData;
import com.test.metrics.model.ClientEventUploadRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
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
        ClientEventUploadRequest uploadRequest = new ClientEventUploadRequest();
        try {
            ProtobufIOUtil.mergeFrom(is, uploadRequest, ClientEventUploadRequest.getSchema());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ClientMetricsResourceV1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
        
        if (uploadRequest.getEventsCount()==0) {
            return Response.ok().build();
        }
        
        for (ClientEventData itr: uploadRequest.getEventsList()) {
            dbAccess.storeEvent(itr);
        }
        
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostJson(InputStream is)
    {
        log.trace("handlePostJson(InputStream)");
        ClientEventUploadRequest uploadRequest = new ClientEventUploadRequest();
        try {
            JsonIOUtil.mergeFrom(is, uploadRequest, ClientEventUploadRequest.getSchema(), false);
        } catch (JsonInputException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
            
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ClientMetricsResourceV1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
        
        for (ClientEventData itr: uploadRequest.getEventsList()) {
            dbAccess.storeEvent(itr);
        }
        
        return Response.ok().build();
        
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleGetJson(@PathParam("id") String id)
    {
        log.trace("handleGetJson(String)");
        if (!isNumber(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        ClientEventData data = dbAccess.retrieveEvent(id);
        if (data==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        byte[] bytes = JsonIOUtil.toByteArray(data, ClientEventData.getSchema(), false);

        return Response.ok(bytes, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("{id}")
    @Produces(JerseyProtostuff.APPLICATION_PROTOBUF)
    public Response handleGetProtobuf(@PathParam("id") String id)
    {
        log.trace("handleGetProtobuf(String)");
        if (!isNumber(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        ClientEventData data = dbAccess.retrieveEvent(id);
        if (data==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        byte[] bytes = ProtobufIOUtil.toByteArray(data, ClientEventData.getSchema(),
                                                  LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return Response.ok(bytes, JerseyProtostuff.APPLICATION_PROTOBUF).build();
       
    }
    
    private boolean isNumber(String num) {
       try { 
           Integer.parseInt(num); 
       } catch(NumberFormatException e) { 
           return false; 
       }
       
       return true;
    }
    
    /*private boolean validateClientEventUploadRequest(ClientEventUploadRequest request) {
        if (request.get)
    }*/
}
