package com.example.toneanalysis.http.api;

import com.example.toneanalysis.MainActivity;
import com.example.toneanalysis.http.HttpClient;
import com.example.toneanalysis.http.dto.classes.ToneAnalysisRequestDTO;
import com.example.toneanalysis.http.dto.classes.ToxicityAnalysisRequestDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ApiWorker {
    private CompletableFuture<String> currentFuture = null;
    private final HttpClient httpClient = new HttpClient(10000, 10000);   // TODO remove hard code
    private final String toneAnalysisServiceURL = "http://10.0.2.2:8080/analysis";  // TODO remove hard code
    private final String toxicityAnalysisServiceURL = "http://10.0.2.2:8000/analysis";
    public void makeToneAnalysis(MainActivity context, String inputText) {
        if (inputText.length() == 0) {return;}  // don't handle empty text
        currentFuture = CompletableFuture.supplyAsync(() -> {
            ToneAnalysisRequestDTO requestDTO = new ToneAnalysisRequestDTO();
            requestDTO.text = inputText;
            try {
                return httpClient.requestPost(toneAnalysisServiceURL, ToneAnalysisRequestDTO.toJsonString(requestDTO));
            } catch (IOException e) {
                throw new RuntimeException(e);  // TODO change on custom exception
            }
        });
        currentFuture.exceptionally(exception -> {
            System.err.println("API request failed: " + exception.getLocalizedMessage());
            // ignore string result
            return null;
        });
        // next call is in UI thread:
        currentFuture.thenAccept(result ->
                context.runOnUiThread(() ->
                        context.toneAnalysisCompleteCallback(result)
                ));
    }
    public void makeToxicityAnalysis(MainActivity context, String inputText) {
        if (inputText.length() == 0) {return;}
        currentFuture = CompletableFuture.supplyAsync(() -> {
            ToxicityAnalysisRequestDTO requestDTO = new ToxicityAnalysisRequestDTO();
            requestDTO.text = inputText;
            try {
                return httpClient.requestPost(toxicityAnalysisServiceURL, ToxicityAnalysisRequestDTO.toJsonString(requestDTO));
            } catch (IOException e) {
                throw new RuntimeException(e);  // TODO change on custom exception
            }
        });
        currentFuture.exceptionally(exception -> {
            System.err.println("API request failed: " + exception.getLocalizedMessage());
            // ignore string result
            return null;
        });
        // next call is in UI thread:
        currentFuture.thenAccept(result ->
                context.runOnUiThread(() ->
                        context.toxicityAnalysisCompleteCallback(result)
                ));
    }
    public boolean isWorking() {
        return !(currentFuture == null || currentFuture.isDone());
    }
}
