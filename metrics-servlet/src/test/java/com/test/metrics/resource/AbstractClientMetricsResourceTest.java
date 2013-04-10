package com.test.metrics.resource;

import com.dyuproject.protostuff.JsonIOUtil;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.test.metrics.AbstractGuiceJerseyTest;
import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.jersey.JerseyProtostuff;
import com.test.metrics.model.ClientEventData;
import com.test.metrics.model.ClientEventType;
import com.test.metrics.model.ClientEventUploadRequest;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Basic test cases.
 *
 * TODO: Write more tests.
 *
 */
public abstract class AbstractClientMetricsResourceTest extends AbstractGuiceJerseyTest
{
    @Inject
    DatabaseAccess dbAccess;

    protected abstract String getRootResourceUrl();

    @Test
    public void test_PostNoEvents()
    {
        log.trace("test_PostNoEvents");
        ClientEventUploadRequest uploadRequest = new ClientEventUploadRequest("1", "Android");
        byte[] bytes = ProtobufIOUtil.toByteArray(uploadRequest, ClientEventUploadRequest.getSchema(),
                                                  LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

        ClientResponse postResponse = resource().path(getRootResourceUrl()).type(JerseyProtostuff.APPLICATION_PROTOBUF).post(ClientResponse.class, bytes);
        assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());
    }

    @Test
    public void test_PostWithEventsAsJson()
    {
        log.trace("test_PostWithEventsAsJson");
        ClientEventUploadRequest uploadRequest = new ClientEventUploadRequest("1234", "Android");
        uploadRequest.addEvents(new ClientEventData("1", ClientEventType.UNKNOWN, 1L));

        byte[] bytes = JsonIOUtil.toByteArray(uploadRequest, ClientEventUploadRequest.getSchema(), false);

        ClientResponse postResponse = resource().path(getRootResourceUrl()).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, bytes);
        assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

        ClientEventData event = dbAccess.retrieveEvent("1");
        assertNotNull("Event was not stored.", event);
        assertEquals("1", event.getEventId());
        assertEquals(ClientEventType.UNKNOWN, event.getEventType());
        assertEquals(1L, event.getTimestamp().longValue());
    }

    @Test
    public void test_GetProtobuf()
    {
        log.trace("test_GetProtobuf");

        // pre-fill database with an event
        dbAccess.storeEvent(new ClientEventData("2", ClientEventType.UNKNOWN, 1L));

        ClientResponse getResponse = resource().path(getRootResourceUrl() + "/2").accept(JerseyProtostuff.APPLICATION_PROTOBUF).get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        assertTrue(getResponse.getHeaders().getFirst("Content-Type").contains(JerseyProtostuff.APPLICATION_PROTOBUF));

        try {
            ClientEventData event = new ClientEventData();
            InputStream in = getResponse.getEntityInputStream();
            ProtobufIOUtil.mergeFrom(in, event, ClientEventData.getSchema());
            assertNotNull(event);
            assertEquals("2", event.getEventId());
            assertEquals(ClientEventType.UNKNOWN, event.getEventType());
            assertEquals(1L, event.getTimestamp().longValue());
        }
        catch (Throwable t) {
            fail(t.getMessage());
        }
    }

    @Test
    public void test_GetDefaultJsonNoAccept()
    {
        log.trace("test_GetDefaultJsonNoAccept");

        // pre-fill database with an event
        dbAccess.storeEvent(new ClientEventData("3", ClientEventType.UNKNOWN, 1L));

        ClientResponse getResponse = resource().path(getRootResourceUrl() + "/3").get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        assertTrue(getResponse.getHeaders().getFirst("Content-Type").contains(MediaType.APPLICATION_JSON));
    }
}
