package com.test.metrics.jersey;

import com.dyuproject.protostuff.Message;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
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
public class ProtostuffMessageBodyReader implements MessageBodyReader<Message>
{
    private static final Logger log = LoggerFactory.getLogger(ProtostuffMessageBodyReader.class);

    @Override
    public boolean isReadable(Class msgClass, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return false;
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
    {
        throw new UnsupportedOperationException("implement me");
    }
}