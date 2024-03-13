package com.example.toneanalysis.http.dto.classes;

import com.google.gson.Gson;

public class ToneAnalysisRequestDTO {
    public String text;

    private static final Gson jsonParser = new Gson();

    public static String toJsonString(ToneAnalysisRequestDTO requestDTO) {
        return jsonParser.toJson(requestDTO);
    }
}
