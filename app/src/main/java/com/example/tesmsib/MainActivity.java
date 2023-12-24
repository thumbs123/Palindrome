package com.example.tesmsib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, sentenceInput;
    private Button checkButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        sentenceInput = findViewById(R.id.sentenceInput);
        checkButton = findViewById(R.id.checkButton);
        nextButton = findViewById(R.id.nextButton);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPalindrome();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkPalindrome() {
        String sentence = sentenceInput.getText().toString().replaceAll("\\s", "");
        String reversedSentence = new StringBuilder(sentence).reverse().toString();

        boolean isPalindrome = sentence.equalsIgnoreCase(reversedSentence);

        String message = isPalindrome ? "isPalindrome" : "not palindrome";
        showDialog(message);
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Palindrome Check")
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}