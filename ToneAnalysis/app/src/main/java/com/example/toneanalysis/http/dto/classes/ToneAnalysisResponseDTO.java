package com.example.toneanalysis.http.dto.classes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ToneAnalysisResponseDTO {
    public String text;
    public String analysis_mask;
    private static final Gson gsonParser = new Gson();

    public static ToneAnalysisResponseDTO parseDTO(String jsonString) throws JsonSyntaxException {
        return gsonParser.fromJson(jsonString, ToneAnalysisResponseDTO.class);
    }
}
