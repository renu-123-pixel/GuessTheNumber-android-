package com.examplefourthjuly.guessthenumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Random r = new Random();
    private int random;
    private int remainingRight = 10;
    private boolean twoDigits, threeDigits, fourDigits;
    private TextView textLast, textRight, textHint;
    private EditText edittxt1;
    private Button btn1;

    private ArrayList<Integer> guesslist = new ArrayList<>();
    private int userAttempt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize UI elements
        textLast = findViewById(R.id.textView3);
        textRight = findViewById(R.id.textView4);
        textHint = findViewById(R.id.textView5);
        edittxt1 = findViewById(R.id.editTextGuess);
        btn1 = findViewById(R.id.buttonConfirm);

        // Get difficulty selection from MainActivity
        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        // Generate random number based on difficulty level
        if (twoDigits) {
            random = r.nextInt(90) + 10; // 10-99
        } else if (threeDigits) {
            random = r.nextInt(900) + 100; // 100-999
        } else if (fourDigits) {
            random = r.nextInt(9000) + 1000; // 1000-9999
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = edittxt1.getText().toString().trim();
                if (guess.isEmpty()) {
                    Toast.makeText(GameActivity.this, "Please enter a number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int userGuess;
                try {
                    userGuess = Integer.parseInt(guess);
                } catch (NumberFormatException e) {
                    Toast.makeText(GameActivity.this, "Invalid input! Please enter a number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show UI elements
                textLast.setVisibility(View.VISIBLE);
                textHint.setVisibility(View.VISIBLE);
                textRight.setVisibility(View.VISIBLE);

                userAttempt++;
                remainingRight--;
                guesslist.add(userGuess);

                textRight.setText("Your remaining attempts: " + remainingRight);
                textLast.setText("Your last guess: " + userGuess);

                if (random == userGuess) {
                    textHint.setText(" Hurray! You guessed it correctly!");

                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle("Number Guessing Game");
                    builder.setCancelable(false);
                    builder.setMessage("Congratulations! \n\nMy number was " + random +
                            ". You guessed it in " + userAttempt + " attempts.\n\nWould you like to play again?");
                    builder.setPositiveButton("YES", (dialog, which) -> {
                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                        finish();
                    });

                    builder.setNegativeButton("NO", (dialog, which) -> finish());

                    builder.create().show();
                } else if (remainingRight == 0) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(GameActivity.this);
                    builder2.setTitle("Game Over! ");
                    builder2.setCancelable(false);
                    builder2.setMessage("Sorry! The correct number was " + random +
                            ".\nYour last guess was: " + userGuess +
                            "\n\nWould you like to try again?");
                    builder2.setPositiveButton("YES", (dialog, which) -> {
                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                        finish();
                    });

                    builder2.setNegativeButton("NO", (dialog, which) -> finish());

                    builder2.create().show();
                }
                else {
                    textHint.setText(random > userGuess ? "Increase your guess number." : "Decrease your guess number.");
                }

                edittxt1.setText(""); // Clear input field
            }
        });
    }
}
