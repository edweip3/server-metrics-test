package com.test.metrics.resource;

import com.test.metrics.db.DatabaseAccess;
import com.test.metrics.db.InMemoryDatabaseAccess;
import org.junit.Ignore;

/**
 *
 */
public class ClientMetricsResourceV1Test extends AbstractClientMetricsResourceTest
{
    private static final String RESOURCE_URL = "/v1/events";

    @Override
    protected String getRootResourceUrl()
    {
        return RESOURCE_URL;
    }

    @Override
    protected void configure()
    {
        bind(ClientMetricsResourceV1.class);
        bind(DatabaseAccess.class).to(InMemoryDatabaseAccess.class);
    }
}
