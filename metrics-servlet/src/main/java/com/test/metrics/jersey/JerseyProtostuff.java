package com.test.metrics.jersey;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import javax.ws.rs.core.MediaType;

public enum JerseyProtostuff
{
    ;

    public static final MediaType APPLICATION_PROTOBUF_TYPE = new MediaType("application", "x-protobuf");
    public static final String APPLICATION_PROTOBUF = "application/x-protobuf";

    public static Module module()
    {
        return new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(ProtostuffMessageBodyReader.class);
                bind(ProtostuffMessageBodyWriter.class);
            }
        };
    }
}
