package com.csc498g.bliss;

import org.json.JSONArray;

public class Response {

    String error;
    boolean success;
    boolean is_authenticated;
    JSONArray query_result;
    boolean is_available;

    public Response(String error, boolean success, boolean is_authenticated, JSONArray query_result, boolean is_available) {
        this.error = error;
        this.success = success;
        this.is_authenticated = is_authenticated;
        this.query_result = query_result;
        this.is_available = is_available;
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

    public JSONArray getQuery_result() {
        return query_result;
    }

    public void setQuery_result(JSONArray query_result) {
        this.query_result = query_result;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

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
