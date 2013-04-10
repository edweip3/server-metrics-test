package com.test.metrics.db;

import com.google.inject.Singleton;
import com.test.metrics.model.ClientEventData;

/**
 * In memory implementation of the {@link DataAccess} interface.
 */
@Singleton
public class InMemoryDatabaseAccess implements DatabaseAccess
{
    @Override
    public void storeEvent(ClientEventData event)
    {
        // TODO: implement
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public ClientEventData retrieveEvent(String eventId)
    {
        // TODO: implement
        throw new UnsupportedOperationException("implement me");
    }
}
