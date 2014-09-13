package net.apetheory.publicise.server.data.database.listener;

import net.apetheory.publicise.server.data.database.Database;

/**
 * Listener used to notify a listener that the
 * connection is established
 */
public interface OnConnectionErrorListener {

    /**
     * Callback which is called on receiving an error
     * @param error The occurred error
     */
    void onError(Database.Error error);
}