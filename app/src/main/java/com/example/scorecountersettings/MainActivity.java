package com.example.scorecountersettings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int team1Counter = 0;
    private int team2Counter = 0;
    private int changeColor;

    private TextView team1View;
    private TextView team2View;
    private TextView sendWinner;

    public static final String WINNER_MESSAGE =
            "com.example.android.ScoreCounterExplicitIntent.winner.MESSAGE";
    public static final String KEY_AWARD = "medal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        team1View  = findViewById(R.id.team1score);
        team2View  = findViewById(R.id.team2score);
        sendWinner = findViewById(R.id.giveWinner);

        changeColor = ContextCompat.getColor(this ,R.color.colorPrimaryDark);

       android.support.v7.preference.PreferenceManager
                .setDefaultValues(this, R.xml.preferences, false);
       PreferenceManager.setDefaultValues(this, R.xml.winner_pic_preference, false);


        SharedPreferences sharedPreferences = android.support.v7.preference
               .PreferenceManager.getDefaultSharedPreferences(this);

            String sports     = sharedPreferences.getString("sync_frequency", "1");
            String KEY_AWARD  = sharedPreferences.getString("win_pic","1");
            String fav_team   = sharedPreferences.getString("favorite_team", "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_favorites){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_contact){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void team1_increment(View view) {
        team1Counter++;
        team1View.setText(Integer.toString(team1Counter));
        if(isWinner(team1Counter)){
            Intent intent = new Intent(this, WinnerActivity.class);
            sendWinner.setVisibility(View.INVISIBLE);
            sendWinner.setText("Raptors won by "+(team1Counter-team2Counter));
            String message = sendWinner.getText().toString();
            intent.putExtra(WINNER_MESSAGE, message);
            startActivity(intent);
        }

    }

    public void team1_decrement(View view) {
        team1Counter--;
        team1View.setText(Integer.toString(team1Counter));

    }

    public void team2_increment(View view) {
        team2Counter++;
        team2View.setText(Integer.toString(team2Counter));
        if(isWinner(team2Counter)){
            Intent intent = new Intent(this, WinnerActivity.class);
            sendWinner.setVisibility(View.INVISIBLE);

            sendWinner.setText("Warriors won by "+(team2Counter-team1Counter));
            String message = sendWinner.getText().toString();
            intent.putExtra(WINNER_MESSAGE, message);
            startActivity(intent);
        }



    }

    public void team2_decrement(View view) {
        team2Counter--;
        team2View.setText(Integer.toString(team2Counter));

    }
    public boolean isWinner(int counter){
        return counter == 5;
    }

    public void newBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        team1View.setBackgroundColor(color);
        team2View.setBackgroundColor(color);
        changeColor = color;

    }
}
