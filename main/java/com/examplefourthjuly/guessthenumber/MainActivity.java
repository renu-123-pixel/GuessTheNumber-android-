package com.examplefourthjuly.guessthenumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private RadioButton radioButton4, radioButton5, radioButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        button = findViewById(R.id.button);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);

                if (!radioButton6.isChecked() && !radioButton5.isChecked() && !radioButton4.isChecked()) {
                    Snackbar.make(view, "Please select the number of digits", Snackbar.LENGTH_LONG).show();
                    return ;
                } else {
                    if (radioButton4.isChecked()) {
                        i.putExtra("two", true);
                    } else if (radioButton5.isChecked()) {
                        i.putExtra("three", true);
                    } else if (radioButton6.isChecked()) {
                        i.putExtra("four", true);
                    }
                    startActivity(i); // ✅ Now only starts when a radio button is selected
                }
            }
        });

        // ✅ Prevent crash if "main" view does not exist
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}
