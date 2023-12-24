package com.example.tesmsib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView welcomeText, showNameText, selectedUserNameLabel;
    private Button chooseUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        welcomeText = findViewById(R.id.welcomeText);
        chooseUserButton = findViewById(R.id.chooseUserButton);

        selectedUserNameLabel = findViewById(R.id.selectedUserNameLabel);

        Intent intent = getIntent();
        if (intent.hasExtra("userName")) {
            String userName = intent.getStringExtra("userName");
            showNameText.setText(userName);
            updateSelectedUserName(this, userName);
        }

        chooseUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("dataTambahan", "Beberapa data tambahan");
                startActivityForResult(intent, 1);
            }
        });
    }

    public static void updateSelectedUserName(Context context, String userName) {
        if (context instanceof SecondActivity) {
            ((SecondActivity) context).setSelectedUserName(userName);
        }
    }

    private void setSelectedUserName(String userName) {
        if (selectedUserNameLabel != null) {
            selectedUserNameLabel.setText(userName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("selectedUserName")) {
                    String selectedUserName = data.getStringExtra("selectedUserName");
                    updateSelectedUserName(this, selectedUserName);
                }
            }
        }
    }
}
