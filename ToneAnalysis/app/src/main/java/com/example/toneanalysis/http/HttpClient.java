package com.example.toneanalysis.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private int connectTimeout;
    private int readTimeout;

    public HttpClient(int readTimeout, int connectTimeout) {
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }
    public void setConnectTimeout(int connectTimeout) {this.connectTimeout = connectTimeout;}
    public void setReadTimeout(int readTimeout) {this.readTimeout = readTimeout;}

    private String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder contentBuilder = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            contentBuilder.append(inputLine);
        }
        reader.close();
        // return JSON data
        return contentBuilder.toString();
    }
    public String requestGet(String urlString) throws IOException {
        final URL url = new URL(urlString);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);

        return readResponse(con);
    }
    public String requestPost(String urlString, String jsonBody) throws IOException {
        final URL url = new URL(urlString);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        // set request and response body types
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);
        con.setDoOutput(true);

        // write body:
        try(OutputStream output = con.getOutputStream()) {
            byte[] bodyData = jsonBody.getBytes(StandardCharsets.UTF_8);
            output.write(bodyData, 0, bodyData.length);
        }
        // read response:
        return readResponse(con);
    }
}