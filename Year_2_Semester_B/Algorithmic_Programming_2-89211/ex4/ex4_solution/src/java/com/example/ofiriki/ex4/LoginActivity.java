package com.example.ofiriki.ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText ip;
    private EditText port;

    /**
     * this function creates the activity.
     * @param savedInstanceState - the state of the last instance that ran
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // save the EditTexts' values to the class
        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);
    }

    /**
     * this method is called when the "connect" button is pressed and
     * starts the JoistickActivity and passes it the ip and port.
     * @param view - the button that was pressed
     */
    protected void Connect(View view) {
        //create the intent to the new activity
        Intent intet = new Intent(this,JoystickActivity.class);
        String serverIP = ip.getText().toString();
        int serverPort = Integer.parseInt(port.getText().toString());
        // save the ip and port the user entered to the intent
        intet.putExtra("ip",serverIP);
        intet.putExtra("port",serverPort);

        startActivity(intet);
    }
}
