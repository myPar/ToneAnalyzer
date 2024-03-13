package com.example.toneanalysis.http.dto.classes;

import com.google.gson.Gson;

public class ToxicityAnalysisRequestDTO {
    public String text;
    private static final Gson jsonParser = new Gson();

    public static String toJsonString(ToxicityAnalysisRequestDTO requestDTO) {
        return jsonParser.toJson(requestDTO);
    }
}
