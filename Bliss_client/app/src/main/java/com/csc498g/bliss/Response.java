package com.csc498g.bliss;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

public class Response {

    String error;
    boolean success;
    boolean is_authenticated;
    int last_id;
    JSONArray query_results;
    JSONObject query_result;
    boolean is_available;

    public Response(int last_id, String error, boolean success, boolean is_authenticated, JSONArray query_results, boolean is_available) {
        this.last_id = last_id;
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_results = query_results;
        this.is_available = is_available;
    }

    public Response(int last_id, String error, boolean success, boolean is_authenticated, JSONObject query_result, boolean is_available) {
        this.last_id = last_id;
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_result = query_result;
        this.is_available = is_available;
    }


    public JSONObject getQuery_result() {
        return query_result;
    }

    public void setQuery_result(JSONObject query_result) {
        this.query_result = query_result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isIs_authenticated() {
        return is_authenticated;
    }

    public void setIs_authenticated(boolean is_authenticated) {
        this.is_authenticated = is_authenticated;
    }

    public JSONArray getQuery_results() {
        return query_results;
    }

    public void setQuery_results(JSONArray query_results) {
        this.query_results = query_results;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
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
