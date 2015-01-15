package com.julia.bookshelf.model.http;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Julia on 09.01.2015.
 */
public class HTTPClient {
    public static String get(String path) {
        BufferedReader in = null;
        StringBuilder stringBuilder = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            addHeaders(connection);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            Log.w("BOOKSHELF",e.toString());
        }
        finally {
            closeStream(in);
        }
        return stringBuilder.toString();
    }

    private static void addHeaders(URLConnection connection) {
        connection.setRequestProperty(Keys.APPlCATION_ID_KEY, Keys.APPlCATION_ID_VALUE);
        connection.setRequestProperty(Keys.REST_API_KEY, Keys.REST_API_VALUE);
    }

    private static void closeStream(BufferedReader in) {
        if (in!=null){
            try {
                in.close();
            } catch (IOException e) {
                Log.w("BOOKSHELF",e.toString());
            }
        }
    }

    public static void post(String path, JSONObject jsonObject) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("title", "Серця трьох");
//            jsonObject.put("author", "Jack London");
//            jsonObject.put("cover", "http://a.wattpad.com/cover/10282042-256-k765626.jpg");
//            jsonObject.put("genre", "fiction");
//            jsonObject.put("annotation", "Francis Morgan, a wealthy heir of industrialist and Wall Street maven Richard Henry Morgan, is a jaded young New Yorker.");
//            jsonObject.put("isFavourite", true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String urlParameters = jsonObject.toString();
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            addHeaders(connection);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
//            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            int i = connection.getResponseCode();
            System.out.println("Response:" + i);
        } catch (IOException e) {
            Log.w("BOOKSHELF",e.toString());
        }
    }

}
