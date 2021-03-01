package com.pyrandom.generator.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.pyrandom.generator.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Integer mCurrentSeed;
    private int mIteration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText seedEditText = findViewById(R.id.seed);
        final EditText lowerBoundEditText = findViewById(R.id.lower_bound);
        final EditText upperBoundEditText = findViewById(R.id.upper_bound);
        final Button generateButton = findViewById(R.id.button_generate);
        final TextView resultTextView = findViewById(R.id.result);
        final Button clearButton = findViewById(R.id.button_clear);

        generateButton.setOnClickListener(v -> {
            Integer seed;
            int lowerBound, upperBound;
            try {
                seed = Integer.parseInt(seedEditText.getText().toString());
            } catch (NumberFormatException e) {
                seed = null;
            }
            try {
                lowerBound = Integer.parseInt(lowerBoundEditText.getText().toString());
            } catch (NumberFormatException e) {
                lowerBound = 0;
            }
            try {
                upperBound = Integer.parseInt(upperBoundEditText.getText().toString());
            } catch (NumberFormatException e) {
                upperBound = lowerBound + 100;
            }

            if (seed == null || !seed.equals(mCurrentSeed)) {
                mCurrentSeed = seed;
                mIteration = 0;
            }

            String result = lowerBound >= upperBound ? "Upper must be greater than lower" :
                    "" + getKey(seed, lowerBound, upperBound, mIteration++);
            resultTextView.setText(result);
        });

        clearButton.setOnClickListener(v -> {
            seedEditText.setText("");
            lowerBoundEditText.setText("");
            upperBoundEditText.setText("");
            resultTextView.setText("");
            seedEditText.requestFocus();
        });

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    private int getKey(Integer seed, int lowerBound, int upperBound, int iteration) {
        Python python = Python.getInstance();
        PyObject pythonModule = python.getModule("gen_num");
        return pythonModule.callAttr("gen_num", seed, lowerBound, upperBound, iteration).toInt();
    }
}