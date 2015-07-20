package com.beerrun.beerrun;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import android.util.Log;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ServerSetup extends Activity {
    private static final String TAG = "messages";

    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serversetup);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(getBaseContext(), Communicate.class);
            intent.putExtra("address", editTextAddress
                    .getText().toString());
            intent.putExtra("port", Integer.parseInt(editTextPort
                    .getText().toString()));
            startActivity(intent);
        }
    };
}