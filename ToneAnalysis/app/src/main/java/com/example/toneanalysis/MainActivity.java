package com.example.toneanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.toneanalysis.http.api.ApiWorker;
import com.example.toneanalysis.http.dto.classes.ToneAnalysisResponseDTO;
import com.example.toneanalysis.http.dto.classes.ToxicityAnalysisResponseDTO;
import com.example.toneanalysis.http.tools.TextWorker;
import com.google.gson.JsonSyntaxException;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    public static final String TONE_ANALYSIS = "Tone analysis";
    public static final String TOXICITY_ANALYSIS = "Toxicity analysis";
    private String currentMode;

    private EditText mainTextWindow;
    private TextView toxicityLevelCaption;
    private ProgressBar toxicityLevelBar;
    private ApiWorker apiWorker;
    private TextWorker textWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // configure analysis button
        Button analysisButton = findViewById(R.id.analysis_button);
        analysisButton.setOnClickListener(view -> listenAnalysisButtonClick());
        // configure toxicity level caption and progress bar
        toxicityLevelCaption = findViewById(R.id.toxicity_level_caption);
        toxicityLevelBar = findViewById(R.id.toxicity_level_bar);
        toxicityLevelCaption.setVisibility(View.INVISIBLE);
        toxicityLevelBar.setVisibility(View.INVISIBLE);

        // configure analysis mode spinner:
        Spinner analysisModeSpinner = findViewById(R.id.analysis_mode_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dropdown_analysis_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        analysisModeSpinner.setAdapter(adapter);
        analysisModeSpinner.setOnItemSelectedListener(new SelectAnalysisModeSpinnerListener());

        // configure text window
        mainTextWindow = findViewById(R.id.main_text_window);

        // init components
        apiWorker = new ApiWorker();
        textWorker = new TextWorker();
    }

    private class SelectAnalysisModeSpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentMode = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    // callbacks:
    public void toneAnalysisCompleteCallback(String result) {
        try {
            ToneAnalysisResponseDTO responseDTO = ToneAnalysisResponseDTO.parseDTO(result);
            String text = responseDTO.text;
            String mask = responseDTO.analysis_mask;

            toxicityLevelCaption.setVisibility(View.INVISIBLE);
            toxicityLevelBar.setVisibility(View.INVISIBLE);

            Spannable spannable = textWorker.highlightTextByMask(text, mask, TONE_ANALYSIS);
            mainTextWindow.setText(spannable);
        }
        catch (JsonSyntaxException e) {
            System.err.println("Invalid API response, maybe exception occurred");
        }
    }

    public void toxicityAnalysisCompleteCallback(String result) {
        try {
            ToxicityAnalysisResponseDTO responseDTO = ToxicityAnalysisResponseDTO.parseDTO(result);
            String text = responseDTO.text;
            String mask = responseDTO.analysis_mask;
            float toxicityRatio = responseDTO.toxicity_ratio;

            Spannable spannable = textWorker.highlightTextByMask(text, mask, TOXICITY_ANALYSIS);
            mainTextWindow.setText(spannable);

            // display toxicity ratio
            float percentRatio = toxicityRatio * 100;
            toxicityLevelBar.setProgress(Math.round(percentRatio));
            String captionText = "toxicity level = " + Math.round(percentRatio) + "%";

            toxicityLevelCaption.setVisibility(View.VISIBLE);
            toxicityLevelBar.setVisibility(View.VISIBLE);

            toxicityLevelCaption.setText(captionText);
        }
        catch (JsonSyntaxException e) {
            System.err.println("Invalid API response, maybe exception occurred");
        }
    }
    private void listenAnalysisButtonClick() {
        String enteredText = mainTextWindow.getText().toString();
        if (currentMode.equals(TONE_ANALYSIS)) {
            if (apiWorker.isWorking()) {return;}    // previous api call is not completed
            apiWorker.makeToneAnalysis(this, enteredText);
        } else if (currentMode.equals(TOXICITY_ANALYSIS)) {
            if (apiWorker.isWorking()) {return;}    // previous api call is not completed
            apiWorker.makeToxicityAnalysis(this, enteredText);
        }
        else {assert false;}
    }
}