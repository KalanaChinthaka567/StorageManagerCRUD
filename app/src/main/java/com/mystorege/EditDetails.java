package com.mystorege;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDetails extends AppCompatActivity {
    EditText etDimensions;
    EditText etRental;
    EditText etNotes;
    EditText etReporter;
    Spinner spinnerStorageType;
    Spinner spinnerFeatures;
    Button buttonSubmit;

    DatabaseReference databaseStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseApp.initializeApp(EditDetails.this);

        databaseStorage = FirebaseDatabase.getInstance().getReference().child("Global");

        etDimensions = (EditText)findViewById(R.id.etDimensions);
        etRental = (EditText)findViewById(R.id.etRental);
        etNotes = (EditText)findViewById(R.id.etNotes);
        etReporter = (EditText)findViewById(R.id.etReporter);

        spinnerStorageType = (Spinner) findViewById(R.id.spinnerStorageType);
        spinnerFeatures = (Spinner) findViewById(R.id.spinnerFeatures);

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        Intent getIntent = getIntent();
        Bundle bundle = getIntent.getExtras();

        if(bundle!=null){
            String storeType = (String)bundle.get("storeType");
            String dimention = String.valueOf(bundle.getDouble("dimention"));
            String storeFeatures = (String)bundle.get("storeFeatures");
            String notes = (String)bundle.get("notes");
            String monthlyRental = String.valueOf(bundle.getInt("monthlyRental"));
            String reporter = (String)bundle.get("reporter");
            final String id = (String)bundle.get("id");

            etDimensions.setText(dimention);
            etRental.setText(monthlyRental);
            etNotes.setText(notes);
            etReporter.setText(reporter);

            spinnerStorageType.setSelection(((ArrayAdapter<String>)spinnerStorageType.getAdapter()).getPosition(storeType));
            spinnerFeatures.setSelection(((ArrayAdapter<String>)spinnerFeatures.getAdapter()).getPosition(storeFeatures));

            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(EditDetails.this);
                    builder.setTitle("Confirm Information");
                    builder.setIcon(R.drawable.bell);
                    builder.setMessage("Storage Type: "+spinnerStorageType.getSelectedItem().toString()+"\nDimensions: "+etDimensions.getText().toString()+"Sq. Meters"+"\nStorage Feature: "+spinnerFeatures.getSelectedItem().toString()
                            +"\nMonthly Rental: "+etRental.getText().toString()+"\nNotes: "+ etNotes.getText().toString()+"\nReporter's Name: "+etReporter.getText().toString());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Changes will be saved", Toast.LENGTH_SHORT).show();
                            System.out.println("//////////////////////////////////////////////////////");
                            databaseStorage.child(id).child("storeType").setValue(spinnerStorageType.getSelectedItem().toString());
                            databaseStorage.child(id).child("storeFeatures").setValue(spinnerFeatures.getSelectedItem().toString());
                            databaseStorage.child(id).child("notes").setValue(etNotes.getText().toString());
                            databaseStorage.child(id).child("reportName").setValue(etReporter.getText().toString());
                            databaseStorage.child(id).child("dimensions").setValue(Double.valueOf(String.valueOf(etDimensions.getText())));
                            databaseStorage.child(id).child("monthlyRental").setValue(Integer.valueOf(String.valueOf(etRental.getText())));

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Changes will not be saved", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            });
            System.out.println("-------------------------------------------------"+storeType);
            System.out.println("-------------------------------------------------"+dimention);
            System.out.println("-------------------------------------------------"+storeFeatures);
            System.out.println("-------------------------------------------------"+notes);
            System.out.println("-------------------------------------------------"+monthlyRental);
            System.out.println("-------------------------------------------------"+reporter);
        }


    }
}
