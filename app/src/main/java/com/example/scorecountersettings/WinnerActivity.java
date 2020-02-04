package com.example.scorecountersettings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {


    private EditText callView;
    private EditText sendView;
    private EditText mapsView;
    private TextView showWinner;

    private ImageView winnerImage;
    private int changeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.WINNER_MESSAGE);


        callView    = findViewById(R.id.call_view);
        mapsView    = findViewById(R.id.search_view);
        sendView    = findViewById(R.id.send_view);
        showWinner  = findViewById(R.id.displayWinner);
        winnerImage = findViewById(R.id.imageView);

        changeColor = ContextCompat.getColor(this ,R.color.colorPrimaryDark);


        //gets the preference for award
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString("win_pic", "1");
        String phoneNum = preferences.getString("contact_number", "0");

        // puts text for winner on the pending text message and shows who won
        showWinner.setText(message);
        sendView.setText(message);

        callView.setText(phoneNum);

        // checks what preference was picked for the award image
        if(value.equalsIgnoreCase("medal")){
            winnerImage.setImageResource(R.drawable.medal);
        }
        else if(value.equalsIgnoreCase("trophy")){
            winnerImage.setImageResource(R.drawable.nba_trophy);
        }
        else{
            winnerImage.setImageResource(R.drawable.thumbs_up);
        }



    }
    public void newBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        winnerImage.setBackgroundColor(color);
        changeColor = color;

    }
    // sends phone call
    public void getCall(View view) {
        String phoneNum = callView.getText().toString();

        Uri phone = Uri.parse("tel:"+phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL, phone); // create the intent

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    // sends message
    public void getSend(View view) {
        Log.d("Implicit Intents", "Start the message");
        String txt = sendView.getText().toString();
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("share")
                .setText(txt)
                .startChooser();
        /*
        Uri uri = Uri.parse(txt);
        Intent intent = new Intent(Intent.ACTION_SEND, uri);
//        SmsManager smsManager = SmsManager.getDefault();
//
//       smsManager.sendTextMessage("212867530", null, "AAAAxt", null, null);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            for(String permission: PERMISSIONS_REQUIRED){
                if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, MY_PERMISSION_REQUEST);
                }
            }
        }
    */
        Log.d("Implicit Intents", "message sent");
    }

    // sends google maps search
    public void getSearch(View view) {
        String place = mapsView.getText().toString(); // grab the location text

        Uri address = Uri.parse("geo:0,0?q=" +place);
        Intent intent = new Intent(Intent.ACTION_VIEW, address); // create the intent

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}
