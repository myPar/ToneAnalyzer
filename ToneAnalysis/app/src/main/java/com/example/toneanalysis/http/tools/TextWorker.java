package com.example.toneanalysis.http.tools;

import android.text.Spannable;
import android.text.SpannableString;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.example.toneanalysis.MainActivity;
import com.example.toneanalysis.R;

import java.util.HashMap;

public class TextWorker {
    private HashMap<String, Integer> colorMap = new HashMap<>();
    private HashMap<String, String> toneLabelMap = new HashMap<>();
    private HashMap<String, String> toxicityLabelMap = new HashMap<>();
    private final String TOXIC = "Toxic";
    private final String ACCEPTANCE = "Acceptance";
    private final String POSITIVE = "Positive";
    private final String NEGATIVE = "Negative";
    private final String NEUTRAL = "Neutral";
    public TextWorker() {
        colorMap.put(TOXIC, Color.RED);
        colorMap.put(ACCEPTANCE, Color.GREEN);
        colorMap.put(POSITIVE, Color.GREEN);
        colorMap.put(NEGATIVE, Color.parseColor("#FF5100"));
        colorMap.put(NEUTRAL, Color.GRAY);

        toneLabelMap.put("p", POSITIVE);
        toneLabelMap.put("n", NEGATIVE);
        toneLabelMap.put("i", NEUTRAL); // indifferent

        toxicityLabelMap.put("t", TOXIC);
        toxicityLabelMap.put("a", ACCEPTANCE);
    }
    private HashMap<String, String> getLabelMap(String analysisType) {
        if (analysisType.equals(MainActivity.TONE_ANALYSIS)) {
            return toneLabelMap;
        }
        else {
            return toxicityLabelMap;
        }
    }
    public Spannable highlightTextByMask(String text, String mask, String analysisType) {
        Spannable spannable = new SpannableString(text);
        String[] tokens = getTextTokens(text);
        assert mask.length() == tokens.length;
        int startIndex = 0;
        int tokenIdx = 0;

        HashMap<String, String> labelMap = getLabelMap(analysisType);

        for (String token: tokens) {
            token = token.trim();       // remove extra whitespaces from token
            startIndex = text.indexOf(token, startIndex);
            int endIndex = startIndex + token.length();
            String labelString = labelMap.get(String.valueOf(mask.charAt(tokenIdx)));

            spannable.setSpan(new ForegroundColorSpan(
                    colorMap.get(labelString)),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tokenIdx++;
            startIndex = endIndex;
        }
        return spannable;
    }

    private String[] getTextTokens(String text) {
        return text.split("[.!?)(]+");
    }
}
