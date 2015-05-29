package com.julia.bookshelf.model.http;

import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HTTPClient {

    public static HTTPResponse get(String path) {
        BufferedReader in = null;
        StringBuilder stringBuilder = null;
        int responseCode = 0;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            connection.connect();
            responseCode = connection.getResponseCode();
            if (responseCode == HttpStatus.SC_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                stringBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            }
        } catch (IOException e) {
            Log.w("BOOKSHELF", e.toString());
        } finally {
            closeStream(in);
        }
        String json = stringBuilder != null ? stringBuilder.toString() : null;
        return new HTTPResponse(responseCode, json);
    }

    private static void addHeaders(URLConnection connection) {
        connection.setRequestProperty(Keys.APPlCATION_ID_KEY, Keys.APPlCATION_ID_VALUE);
        connection.setRequestProperty(Keys.REST_API_KEY, Keys.REST_API_VALUE);
    }

    private static void closeStream(BufferedReader in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                Log.w("BOOKSHELF", e.toString());
            }
        }
    }

    public static HTTPResponse post(String path, JSONObject jsonObject) {
        int responseCode = 0;
        StringBuilder stringBuilder = null;
        BufferedReader in;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonObject.toString());

            responseCode = connection.getResponseCode();
            stringBuilder = new StringBuilder();

            if (responseCode == HttpStatus.SC_CREATED) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                stringBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            }

        } catch (IOException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        String json = stringBuilder != null ? stringBuilder.toString() : null;
        return new HTTPResponse(responseCode, json);
    }

    public static void delete(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "x-www-form-urlencoded");
            connection.connect();
            Log.i("bookshelf", "response from delete: " + connection.getResponseCode());
        } catch (IOException e) {
            Log.w("BOOKSHELF", e.toString());
            ;
        }
    }


}
