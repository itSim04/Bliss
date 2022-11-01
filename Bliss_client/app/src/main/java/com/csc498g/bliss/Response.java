package com.csc498g.bliss;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

public class Response {

    private final String error;
    private final boolean success;
    private final boolean is_authenticated;
    private final int last_id;
    private final JSONArray query_results;
    private final JSONObject query_result;
    private final JSONObject is_available;

    public Response(int last_id, String error, boolean success, boolean is_authenticated, JSONArray query_results, JSONObject is_available) {
        this.last_id = last_id;
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_results = query_results;
        this.query_result = null;
        this.is_available = is_available;
    }

    public Response(int last_id, String error, boolean success, boolean is_authenticated, JSONObject query_result, JSONObject is_available) {
        this.last_id = last_id;
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_result = query_result;
        this.query_results = null;
        this.is_available = is_available;
    }

    public JSONObject getQueryResult() {
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

    public JSONArray getQueryResults() {
        return query_results;
    }

    public JSONObject isAvailable() { return is_available; }

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
                ", query_results=" + query_results +
                ", query_result=" + query_result +
                ", is_available=" + is_available +
                '}';
    }
}
