package com.test.metrics.jersey;

import com.dyuproject.protostuff.Message;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a provider that supports the conversion of {@link Message}
 * objects to a stream.
 */
@Singleton
@Provider
@SuppressWarnings("rawtypes")
public class ProtostuffMessageBodyWriter implements MessageBodyWriter<Message>
{
    private static final Logger log = LoggerFactory.getLogger(ProtostuffMessageBodyWriter.class);

    @Override
    public boolean isWriteable(Class msgClass, Type arg1, Annotation[] arg2, MediaType mediaType)
    {
        return false;
    }

    @Override
    public void writeTo(Message t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public long getSize(Message t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        throw new UnsupportedOperationException("implement me");
    }
}