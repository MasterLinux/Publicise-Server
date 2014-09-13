package net.apetheory.publicise.server.data.database.listener;

import net.apetheory.publicise.server.data.ResourceSet;

/**
* Created by Christoph on 13.09.2014.
*/
public interface OnTransactionCompletedListener {
    void onCompleted(ResourceSet result);
}
