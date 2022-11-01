package com.csc498g.bliss;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Response {

    private final String error;
    private final boolean success;
    private final boolean is_authenticated;
    private final int last_id;
    private final HashMap<String, ArrayList<?>> query_result;
    private final int is_available;

    public Response(int last_id, String error, boolean success, boolean is_authenticated, HashMap<String, ArrayList<?>> query_result, int is_available) {
        this.last_id = last_id;
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_result = query_result;
        this.is_available = is_available;
    }

    public HashMap<String, ArrayList<?>> getQueryResult() {
        return query_result;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isAuthenticated() {
        return is_authenticated;
    }

    public int isAvailable() { return is_available; }

    public int getLastId() {
        return last_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Response{" +
                "error='" + error + '\'' +
                ", success=" + success +
                ", is_authenticated=" + is_authenticated +
                ", query_result=" + query_result +
                ", is_available=" + is_available +
                '}';
    }
}
