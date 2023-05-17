package com.example.calculator_bharat_intern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import com.example.calculator_bharat_intern.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    boolean lastNumeric = false;
    boolean stateError = false;
    boolean lastDot = false;
    private Expression expression;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onEqualClick(View view) {
        onEqual();
        binding.dataTv.setText(binding.resultTv.getText().toString().substring(1));
    }

    public void onDigitClick(View view) {
        if (stateError) {
            binding.dataTv.setText(((Button) view).getText());
            stateError = false;
        } else {
            binding.dataTv.append(((Button) view).getText());
        }
        lastNumeric = true;
        onEqual();
    }

    public void onAllClearClick(View view) {
        binding.dataTv.setText("");
        binding.resultTv.setText("");
        stateError = false;
        lastDot = false;
        lastNumeric = false;
        binding.resultTv.setVisibility(View.GONE);
    }

    public void onOperatorClick(View view) {
        if(!stateError && lastNumeric) {
            binding.dataTv.append(((Button) view).getText());
            lastDot = false;
            lastNumeric = false;
            onEqual();
        }
    }

    public void onClearClick(View view) {
        binding.dataTv.setText("");
        lastNumeric = false;
    }

    public void onBackClick(View view) {
        String text = binding.dataTv.getText().toString();
        String result = text.substring(0, text.length() - 1);
        binding.dataTv.setText(result);
        try {
            String text1 = binding.dataTv.getText().toString();
            char lastChar = text1.charAt(text1.length() - 1);
            if(Character.isDigit(lastChar)) {
                onEqual();
            }
        }
        catch (Exception e) {
            binding.resultTv.setText("");
            binding.resultTv.setVisibility(View.GONE);
            Log.e("last char error", e.toString());
        }
    }

    public void onEqual() {
        if(lastNumeric && !stateError) {
            String txt = binding.dataTv.getText().toString();
            expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                binding.resultTv.setVisibility(View.VISIBLE);
                binding.resultTv.setText("=" + result);
            }
            catch (ArithmeticException ex){
                Log.e("Evaluate Error", ex.toString());
                binding.resultTv.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}