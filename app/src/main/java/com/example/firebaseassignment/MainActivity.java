package com.example.firebaseassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;


    private TextView enterData;
    private TextView databaseReference;

    private TextView retrieveReference;
    private TextView retrievedData;

    private Button setData;
    private Button getData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDatabase();
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromDatabase();
            }
        });
    }

    private void initUI() {
        database          = FirebaseDatabase.getInstance();

        enterData         = findViewById(R.id.enterData);
        databaseReference = findViewById(R.id.databaseReference);
        setData           = findViewById(R.id.setData);

        retrieveReference = findViewById(R.id.retrieveReference);
        retrievedData     = findViewById(R.id.retrievedData);
        getData           = findViewById(R.id.getData);
    }

    private void writeToDatabase() {
        String s = enterData.getText().toString();
        String dbr = databaseReference.getText().toString();

        // Write a message to the database
        DatabaseReference myRef = database.getReference(dbr);

        myRef.setValue(s);
        Toast.makeText(MainActivity.this, "Database Reference: " + dbr + ", is now set to: " + s, Toast.LENGTH_SHORT).show();
    }

    private void readFromDatabase() {
        String r = retrieveReference.getText().toString();
        DatabaseReference myRef = database.getReference(r);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                retrievedData.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
