package com.lara.bmicalc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Class Variables; also are called 'Fields'
    private TextView resultText;
    private Button calculate;
    private RadioButton male;
    private RadioButton female;
    private EditText weight;
    private EditText age;
    private EditText feet;
    private EditText inches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        setUpButtonClickListener();
    }

    private void findViews() {
        resultText = findViewById(R.id.text_view_result);
        male = findViewById(R.id.radio_button_male);
        female = findViewById(R.id.radio_button_female);
        age = findViewById(R.id.edit_text_age);
        feet = findViewById(R.id.edit_text_feet);
        inches = findViewById(R.id.edit_text_inches);
        weight = findViewById(R.id.edit_text_weight);
        calculate = findViewById(R.id.button_calculate);
    }

    private void setUpButtonClickListener() {
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bmiResult = calculateBmi();

                String ageText = age.getText().toString();
                int ageNum = Integer.parseInt(ageText);

                if (ageNum >= 18) {
                    displayResult(bmiResult);
                } else {
                    displayGuidance(bmiResult);
                }
            }
        });
    }

    private double calculateBmi() {
        String feetText = feet.getText().toString();
        String inchesText = inches.getText().toString();
        String weightText = weight.getText().toString();

        // Converting the number 'Strings' into 'int' variables
        int feetInt = Integer.parseInt(feetText);
        int inchesInt = Integer.parseInt(inchesText);
        int weightInt = Integer.parseInt(weightText);

        int totalInches = (feetInt * 12) + inchesInt;

        // Height in meters is the inches multiplied by 0.0254
        double heightInMeters = totalInches * 0.0254;

        // BMI formula = weight in kg divided by height in meters squared
        return weightInt / (heightInMeters * heightInMeters);

    }

    private void displayResult(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5) {
            // Display underweight
            fullResultString = bmiTextResult + " - You are underweight";
        } else if (bmi > 25) {
            // Display overweight
            fullResultString = bmiTextResult + " - You are overweight";

        } else {
            // Display healthy
            fullResultString = bmiTextResult + " - You are a healthy weight";

        }
        resultText.setText(fullResultString);
    }

    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTextResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (male.isChecked()) {
            // Display boy guidance
            fullResultString = bmiTextResult + " - As you are under 18, please consult with your doctor for the healthy range for boys.";
        } else if (female.isChecked()) {
            // Display girl guidance
            fullResultString = bmiTextResult + " - As you are under 18, please consult with your doctor for the healthy range for girls.";

        } else {
            // Display general guidance
            fullResultString = bmiTextResult + " - As you are under 18, please consult with your doctor for the healthy range.";

        }

        resultText.setText(fullResultString);


    }
}