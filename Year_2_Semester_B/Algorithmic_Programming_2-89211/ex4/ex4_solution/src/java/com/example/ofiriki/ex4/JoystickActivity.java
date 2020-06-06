package com.example.ofiriki.ex4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;



public class JoystickActivity extends AppCompatActivity {

    private Joystick joystick;
    private String ip;
    private int port;
    private Socket socket;
    private boolean isPressed;
    private PrintWriter writer;


    /**
     * this function creates the activity.
     * @param savedInstanceState - the state of the last instance that ran
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPressed = false;
        joystick = new Joystick(this);
        setContentView(joystick);
        //reaad the ip and port that was inserted to the intent
        Intent intent = getIntent();
        ip = intent.getStringExtra("ip");
        port = intent.getIntExtra("port",0);

        writer = null;
    }

    /**
     * this method closes the socket when the activity is destroys
     */
    @Override
    protected void onDestroy() {
        try {
            if(socket != null) {
                socket.close();
            }
        }
        // if didn't work
        catch (Exception ex) {
            System.out.println("Execption " + ex.getMessage());
        }
        super.onDestroy();
    }

    /**
     * this method defines what happens when the user touches the mobile
     * @param event - the event
     * @return - true always
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        //act differently - depends on the kind of the touch
        switch(action) {
            case MotionEvent.ACTION_DOWN: {
                isPressed = joystick.isPressed(event.getX(),event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();

                // if the user is pressing the mobile
                if(isPressed) {
                    /*
                    call the gesture method of the jotstick who will move the joystick
                    and save the new values of the aileron and elevator
                     */
                    joystick.gesture(x,y);
                    // send the new values of the aileron and elevator to the server
                    String message = "set /controls/flight/aileron " +  joystick.getAileron() + "\r\n";
                    new ClientTask().execute(message);
                    message = "set /controls/flight/elevator " + joystick.getElevator() + "\r\n";
                    new ClientTask().execute(message);
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                // bring the joystick back to the center
                joystick.resetJoystick();
                break;
            }

            /*case MotionEvent.ACTION_CANCEL: {
                joystick.resetJoystick();
                break;
            }*/
        }
        return true;
    }


    public class ClientTask extends AsyncTask<String, Void, Void> {
        /**
         * this method writes the massage to the server, if we are not connected to the server
         * yet- connects it
         * @param message the massage to send
         * @return null
         */
        @Override
        protected Void doInBackground(String... message) {
            if(socket == null) {
                try {
                    // connecting to the server
                    socket = new Socket(ip, port);
                    OutputStream output = socket.getOutputStream();
                    writer = new PrintWriter(output, true);

                } catch (Exception ex) {
                    System.out.println("Exception " + ex.getMessage());
                    return null;
                }
            }
            // write the massage to the server
            writer.write(message[0]);
            writer.flush();
            return null;
        }
    }

}
