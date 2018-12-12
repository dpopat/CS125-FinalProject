package com.example.natashagoel.snowman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button click;
    public static TextView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.output);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveTask().execute(wordList());
            }
        });
    }


    private String wordList() {
        final String language = "en";
        final String filters = "domains=Art";
        return "https://od-api.oxforddictionaries.com:443/api/v1/wordlist/" + language + "/" + filters;
    }
}
