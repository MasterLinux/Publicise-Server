package net.apetheory.publicise.server.resource;

import net.apetheory.publicise.server.data.utility.*;
import org.bson.*;

/**
 * BSON document which contains each meta information
 * of a requested resource.
 */
public class MetaDocument extends BsonDocument {
    public static final String LIMIT_FIELD = "limit";
    public static final String OFFSET_FIELD = "offset";
    public static final String FILTERED_COUNT_FIELD = "filteredCount";
    public static final String TOTAL_COUNT_FIELD = "totalCount";
    public static final String NEXT_FIELD = "next";
    public static final String PREV_FIELD = "prev";

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;
    private static final long DEFAULT_FILTERED_COUNT = 0;
    private static final long DEFAULT_TOTAL_COUNT = 0;

    public MetaDocument() {
        setOffset(DEFAULT_OFFSET);
        setLimit(DEFAULT_LIMIT);
        setFilteredCount(DEFAULT_FILTERED_COUNT);
        setTotalCount(DEFAULT_TOTAL_COUNT);
        setNext(null);
        setPrev(null);
    }

    public int getOffset() {
        return getInt32(OFFSET_FIELD).getValue();
    }

    public void setOffset(int offset) {
        append(OFFSET_FIELD, new BsonInt32(offset));
    }

    public int getLimit() {
        return getInt32(LIMIT_FIELD).getValue();
    }

    public void setLimit(int limit) {
        append(LIMIT_FIELD, new BsonInt32(limit));
    }

    public long getFilteredCount() {
        return getInt64(FILTERED_COUNT_FIELD).getValue();
    }

    public void setFilteredCount(long filteredCount) {
        append(FILTERED_COUNT_FIELD, new BsonInt64(filteredCount));
    }

    public long getTotalCount() {
        return getInt64(TOTAL_COUNT_FIELD).getValue();
    }

    public void setTotalCount(long totalCount) {
        append(TOTAL_COUNT_FIELD, new BsonInt64(totalCount));
    }

    public String getNext() {
        return getString(NEXT_FIELD).getValue();
    }

    public void setNext(String next) {
        append(NEXT_FIELD, StringUtils.isNullOrEmpty(next) ? BsonNull.VALUE : new BsonString(next));
    }

    public String getPrev() {
        return getString(PREV_FIELD).getValue();
    }

    public void setPrev(String prev) {
        append(PREV_FIELD, StringUtils.isNullOrEmpty(prev) ? BsonNull.VALUE : new BsonString(prev));
    }
}
