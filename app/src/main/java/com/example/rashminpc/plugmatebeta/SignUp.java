package com.example.rashminpc.plugmatebeta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button b  = (Button)findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)findViewById(R.id.editText1);
                String prodID = et.getText().toString();
                Log.d("file",prodID);

                try {
                    writeFile(prodID);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SignUp.this,PlugMateMain.class);
                intent.putExtra("STRING_I_NEED",prodID);
                startActivity(intent);



            }
        });

    }

    protected void writeFile(String data) throws FileNotFoundException {
        //File loginFile = new File(getApplicationContext().getFilesDir(),"login.txt");
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("plugmate_login.txt", Context.MODE_PRIVATE));
            osw.write(data);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
