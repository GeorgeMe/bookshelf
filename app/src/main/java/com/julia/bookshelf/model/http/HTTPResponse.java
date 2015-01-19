package com.julia.bookshelf.model.http;

import org.apache.http.HttpStatus;

/**
 * Created by Julia on 19.01.2015.
 */
public class HTTPResponse {
    private int httpStatus;
    private String json;

    public HTTPResponse(int httpStatus, String json) {
        this.httpStatus = httpStatus;
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public boolean isSuccess(){
        return (httpStatus== HttpStatus.SC_OK)&(json!=null);
    }
}
