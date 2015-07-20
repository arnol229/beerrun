package com.beerrun.beerrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Arnol229 on 7/18/15.
 */

public class Communicate extends Activity {

    private String TAG = "logging";
    private String address;
    private Integer port;
    public Socket socket;
    private PrintWriter out;
    private BufferedReader input;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communicate);
        Log.i(TAG,"initializing connection...");
        // first try to connect with port/address
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            port = extras.getInt("port");
            address = extras.getString("address");
        } else {
            //send back to main screen
            Log.i(TAG,"no extras sent from ServerSetup");
            Intent intent = new Intent(getBaseContext(), ServerSetup.class);
            startActivity(intent);
        }
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        Socket socket = null;
//        Scanner inputScanner;
            // try to connect to server
            Log.i(TAG,"creating socket...");
            Thread thread = new Thread(new Connect(address, port));
            thread.start();
//            dataOutputStream = new DataOutputStream(
//                    this.socket.getOutputStream());
//            dataInputStream = new DataInputStream(
//                    this.socket.getInputStream());
    }
    class Connect implements Runnable {
        public Connect(String address, Integer port) {
        }

        @Override
        public void run() {
            InetAddress serverAddr = null;
            PrintWriter out = null;
            BufferedReader input = null;
            try {
                serverAddr = InetAddress.getByName(address);
                socket = new Socket(serverAddr, port);
                try{
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    Log.i(TAG, "Sending test data");
                    out.println("test");
                    Log.i(TAG, "Waiting for response");
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String data = input.readLine();
                    Log.i(TAG, "successful: " + data);
                } catch (IOException e) {
                    Log.i(TAG,"crappy error making socket: " + e);
                }
            } catch (UnknownHostException e) {
                Log.i(TAG, "Error connecting: " + e);
            } catch (IOException e) {
                Log.i(TAG,"Error connecting: " + e);
            }
        }
    }
}
