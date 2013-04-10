package com.test.metrics.resource;

import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.db.InMemoryDatabaseAccess;
import com.test.metrics.jersey.JerseyProtostuff;
import org.junit.Ignore;

/**
 *
 */
public class ClientMetricsResourceV2Test extends AbstractClientMetricsResourceTest
{
    private static final String RESOURCE_URL = "/v2/events";

    @Override
    protected String getRootResourceUrl()
    {
        return RESOURCE_URL;
    }

    @Override
    protected void configure()
    {
        install(JerseyProtostuff.module());
        bind(ClientMetricsResourceV2.class);
        bind(DatabaseAccess.class).to(InMemoryDatabaseAccess.class);
    }
}
