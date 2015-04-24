package net.apetheory.publicise.server.data.database.listener;

import net.apetheory.publicise.server.data.ApiError;

/**
* Created by Christoph on 13.09.2014.
*/
public interface OnTransactionErrorListener {
    void onError(ApiError error);
}
