package com.example.natashagoel.com;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView header;
    private TextView displayWord;
    private TypedArray listImages;
    private ImageView image;
    private EditText guessEdit;
    private Button guess;
    private TextView amtGuesses;
    private TextView pastGuesses;
    private Button newGame;
    private static TextView data;
    protected static ArrayList<String> dictionary;
    private String randWord;
    private int numOfGuesses = 8;

    public String incorrectGuessedletters;
    public String displayOfCorrectLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveTask().execute(wordList());

        listImages = getResources().obtainTypedArray(R.array.listOfImages);
        header = (TextView) findViewById(R.id.tvHeader);
        displayWord = (TextView) findViewById(R.id.tvLinefGuesses);
        image = (ImageView) findViewById(R.id.imageView);
        guessEdit = (EditText) findViewById(R.id.etInputGuess);
        guess = (Button) findViewById(R.id.bGuess);
        amtGuesses = (TextView) findViewById(R.id.tvAmtOfGuesses);
        pastGuesses = (TextView) findViewById(R.id.tvListOfPastGuesses);
        newGame = (Button) findViewById(R.id.bNewGame);
        //randWord = generateRandWord(dictionary).toUpperCase();
        try {
            newGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newGame( );
                }
            });
        } catch(Exception e) {
            Log.d("HOI", "HELLLLLOOOO -- " + e.getMessage());
        }

        //newGame(null);
    }

    public void newGame() {
        header.setText("Let's Play Hangman!");
        findViewById(R.id.etInputGuess).setVisibility(View.VISIBLE);
        numOfGuesses = 8;
        amtGuesses.setText("Number of Guesses left: " + numOfGuesses);
        pastGuesses.setText("");
        image.setImageResource(listImages.getResourceId(numOfGuesses, 0));
        randWord = generateRandWord(dictionary).toUpperCase();
        displayWord.setText(new String(new char[randWord.length()]).replace("\0", "_  "));
    }

    public void guess(View view) {
        String guess = guessEdit.getText().toString().toUpperCase();

        guessEdit.setText("");

        if (guess.length() > 1 || !guess.matches("[a-zA-Z]+")) {
            Toast.makeText(MainActivity.this, "You must input a single alphabetic character!", Toast.LENGTH_SHORT).show();
            return;
        }

        String wrongGuessesTillNow = pastGuesses.getText().toString();
        String guessesTillNow = displayWord.getText().toString();
        String newGuessesSoFar = "";

        if (guessesTillNow.indexOf(guess) != -1 || wrongGuessesTillNow.indexOf(guess) != -1) {
            Toast.makeText(MainActivity.this, "You've inputted this letter already!", Toast.LENGTH_SHORT).show();
            return;
        }

        Boolean correctGuess = randWord.contains(guess);

        if (correctGuess) {
            for (int i = 0; i < randWord.length(); i++) {
                if (guessesTillNow.contains(randWord.substring(i, i+1)) ||  randWord.charAt(i) == guess.charAt(0)) { //if letter already guessed and displayed or if current guess is this letter
                    newGuessesSoFar += randWord.charAt(i) + "  ";
                } else {
                    newGuessesSoFar += "_  ";
                }
            }
            displayWord.setText(newGuessesSoFar.toUpperCase());
            Toast.makeText(MainActivity.this, "Nice guess!", Toast.LENGTH_SHORT).show();
        } else {
            numOfGuesses--;
            amtGuesses.setText("You have " + numOfGuesses + " incorrect guesses remaining.");
            image.setImageResource(listImages.getResourceId(numOfGuesses, 0));

            String guessedSoFar = pastGuesses.getText().toString().toUpperCase();
            pastGuesses.setText(guessedSoFar + " " + guess.toUpperCase());
            Toast.makeText(MainActivity.this, "Not right.", Toast.LENGTH_SHORT).show();
        }

        newGuessesSoFar = newGuessesSoFar.replaceAll("\\s","");

        if (numOfGuesses == 0) {
            displayWord.setText(randWord.toUpperCase());
            header.setText("You lost!");
            findViewById(R.id.etInputGuess).setVisibility(View.INVISIBLE);
        } else if (randWord.toUpperCase().equals(newGuessesSoFar.toUpperCase())) {
            displayWord.setText(randWord.toUpperCase());
            header.setText("You won!");
            findViewById(R.id.etInputGuess).setVisibility(View.INVISIBLE);
        }
    }

    private String wordList() {
        final String language = "en";
        final String filters = "domains=Computing";
        return "https://od-api.oxforddictionaries.com:443/api/v1/wordlist/" + language + "/" + filters;
    }

    private String generateRandWord(ArrayList<String> list) {
        Random random = new Random();
        int randIndex = random.nextInt(list.size());
        return list.get(randIndex);
    }


//    click = (Button) findViewById(R.id.button);

//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    randomWord = generateRandWord(list);
//                } catch (Exception e) {
//                    randomWord = e.getMessage();
//                }
//                data.setText(randomWord);
//            }
//        });

}
