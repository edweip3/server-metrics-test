package com.test.metrics.db;

import com.test.metrics.model.ClientEventData;

/**
 * Simple interface for storing and retrieving {@link ClientEventData} objects.
 */
public interface DatabaseAccess
{
    /**
     * Stores a {@link ClientEventData} in the database.
     *
     * @param event The event to be stored
     */
    void storeEvent(ClientEventData event);

    /**
     * Retrieves a {@link ClientEventData} from the database.
     *
     * @param eventId The id of the event to retrieve
     * @return The event if found, otherwise null
     */
    ClientEventData retrieveEvent(String eventId);
}
