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
class ListenerTask extends AsyncTask {
    Scanner inputScanner;

    public ListenerTask (Scanner inputScanner){
        this.inputScanner = inputScanner;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        while (true){
            String response = inputScanner.nextLine();
            // if response, set in textbox field in UI
        }
    }
}
public class Communicate extends Activity {

    private String address;
    private Integer port;
    private Scanner inputScanner;
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
        Scanner inputScanner;
        try {
            // try to connect to server
            socket = new Socket(this.address, this.port);
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            inputScanner = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            Intent intent = new Intent(getBaseContext(), ServerSetup.class);
            startActivity(intent);
        } finally {
            ListenerTask socketListener = new ListenerTask(this.inputScanner);
            socketListener.execute();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.communicate);
        }
    };

}
