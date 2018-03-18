package com.example.rashminpc.plugmatebeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class PlugMateMain extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug_mate_main);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        Log.d("file",newString);

        TextView tv1 =(TextView)findViewById(R.id.textView1);
        tv1.setText("Your Product ID is "+newString);

        final DatabaseReference myRefStatus = myRef.child(newString).child("status");
        final DatabaseReference myRefCommand = myRef.child(newString).child("command");


        myRefStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);

                TextView tvstatus = (TextView)findViewById(R.id.statusText);
                tvstatus.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });

        Button changeButton = (Button)findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File dir = getFilesDir();
                File file = new File(dir, "plugmate_login.txt");
                boolean deleted = file.delete();

                Intent intent = new Intent(PlugMateMain.this,SignUp.class);
                startActivity(intent);
            }
        });

        Button onButton = (Button)findViewById(R.id.buttonON);
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRefCommand.setValue("1");
            }
        });


        Button offButton = (Button)findViewById(R.id.buttonOFF);
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRefCommand.setValue("0");
            }
        });
    }
}
