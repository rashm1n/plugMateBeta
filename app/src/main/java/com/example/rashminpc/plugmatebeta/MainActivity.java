package com.example.rashminpc.plugmatebeta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check for exiting account
        Boolean flag1 = false;

        String check = readFromFile(MainActivity.this);

        Log.d("debug",check);


        Button b = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });
    ///////////////////////////////////////////To Delete A File///////////////////////////////////////////////
//        File dir = getFilesDir();
//        File file = new File(dir, "plugmate_login.txt");
//        boolean deleted = file.delete();
        ///////////////////////////////////////////////////////////////



        File f = getFileStreamPath("plugmate_login.txt");

        if (f.length() == 0) {
            Log.d("file","empty");
            // empty or doesn't exist
            flag1 = false;
        } else {
            // exists and is not empty
            flag1 = true;
            Log.d("file","not empty");
        }

        if (!flag1){
            Intent intent = new Intent(MainActivity.this,SignUp.class);
            startActivity(intent);

        }else {
            String prodID = "";
            prodID = readFromFile(MainActivity.this);
            Log.d("file",prodID);

            Intent intent = new Intent(MainActivity.this,PlugMateMain.class);
            intent.putExtra("STRING_I_NEED",prodID);
            startActivity(intent);

        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("plugmate_login.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }else{
                ret = "";
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }



}
