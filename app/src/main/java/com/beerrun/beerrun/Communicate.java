package com.beerrun.beerrun;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Arnol229 on 7/18/15.
 */
public class Communicate extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // first try to connect with port/address
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int port = extras.getInt("port");
            String address = extras.getString("address");
        } else {
            //send back to main screen
            Intent intent = new Intent(getBaseContext(), ServerSetup.class);
            startActivity(intent);
        }
        DataOutputStream dataOutputStream = null;
//        DataInputStream dataInputStream = null;
        Socket socket = null;

        try {
            // try to connect to server
            socket = new Socket(address, port);
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            Scanner inputScanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            Intent intent = new Intent(getBaseContext(), ServerSetup.class);
            startActivity(intent);
        } finally {
            ListenerTask socketListener = new ListenerTask(inputScanner){

            };
            ListenerTask.execute();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.communicate);
        }

        class ListenerTask extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] params) {
                inputScanner = params[0];
                while (true){
                    String response = inputScanner.nextLine();
                    // if response, set in textbox field in UI
                }
            }
        }
    };

}
