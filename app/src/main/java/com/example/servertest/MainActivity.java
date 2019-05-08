package com.example.servertest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Drowsiness Detector");
        new ServerThread().start();
    }

    private class ServerThread extends Thread {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Log.d("SERVER", "New client connected");
                    send();

//
                    Log.d("SERVER", "send function returned");
                    socket.close();
                    Intent i=new Intent(MainActivity.this,emergency.class);
                    startActivity(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void send() {

            int PERMISSION_REQUEST_CODE = 1;
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
            }

            try {
                Log.d("SERVER", "Try to send");
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("7167481383", null, "The sender of this message is feeling sleepy while driving. please save him.", null, null);

                Log.d("SERVER", "Sending");
//                Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "Message not sent", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
