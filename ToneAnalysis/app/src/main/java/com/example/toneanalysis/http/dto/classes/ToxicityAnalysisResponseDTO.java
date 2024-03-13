package com.example.toneanalysis.http.dto.classes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public class ToxicityAnalysisResponseDTO {
    public String analysis_mask;
    public String text;
    public float toxicity_ratio;
    private static final Gson jsonParser = new Gson();
    public static ToxicityAnalysisResponseDTO parseDTO(String jsonString) throws JsonSyntaxException {
        return jsonParser.fromJson(jsonString, ToxicityAnalysisResponseDTO.class);
    }
}
