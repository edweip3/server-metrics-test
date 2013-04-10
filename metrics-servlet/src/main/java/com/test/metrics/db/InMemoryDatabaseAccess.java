package com.test.metrics.db;

import com.google.inject.Singleton;
import com.test.metrics.model.ClientEventData;
import java.util.HashMap;
import java.util.Map;

/**
 * In memory implementation of the {@link DataAccess} interface.
 */
@Singleton
public class InMemoryDatabaseAccess implements DatabaseAccess
{
    //in memory db
    Map<String, ClientEventData> db = new HashMap<String, ClientEventData>();
    
    @Override
    public void storeEvent(ClientEventData event)
    {
        
        db.put(event.getEventId(), event);
    }

    @Override
    public ClientEventData retrieveEvent(String eventId)
    {
        return db.get(eventId);
    }
}
