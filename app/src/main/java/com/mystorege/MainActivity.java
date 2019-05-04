package com.mystorege;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText etDimensions;
    EditText addDate;
    EditText addTime;
    EditText etRental;
    EditText etNotes;
    EditText etReporter;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormat;
    public EditText editCalender;
    public EditText editTime;
    private Toolbar toolbar;
    Calendar myCalendar = Calendar.getInstance();

    Button buttonSubmit;

    Spinner spinnerStorageType;
    Spinner spinnerFeatures;

    DatabaseReference databaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        FirebaseApp.initializeApp(MainActivity.this);

//        Component references
        etDimensions = (EditText)findViewById(R.id.etDimensions);
        addDate = (EditText)findViewById(R.id.addDate);
        addTime = (EditText)findViewById(R.id.addTime);
        etRental = (EditText)findViewById(R.id.etRental);
        etNotes = (EditText)findViewById(R.id.etNotes);
        etReporter = (EditText)findViewById(R.id.etReporter);

        spinnerStorageType = (Spinner) findViewById(R.id.spinnerStorageType);
        spinnerFeatures = (Spinner) findViewById(R.id.spinnerFeatures);

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        databaseStorage = FirebaseDatabase.getInstance().getReference().child("Global");

        editCalender= (EditText) findViewById(R.id.addDate);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" );
        editCalender.setText( sdf.format( new Date() ));

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editCalender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this,date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        editTime= (EditText) findViewById(R.id.addTime);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        String currentTime = sdf1.format(new Date());
        editTime.setText(currentTime);


        editTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Set Time");
                mTimePicker.show();

            }
        });

        if( etDimensions.getText().toString().isEmpty()){
            etDimensions.setError( "Dimensions is required!" );
        }

        if( etRental.getText().toString().isEmpty()){
            etRental.setError( "Monthly Rental is required!" );
        }
        if( etReporter.getText().toString().isEmpty()){
            etReporter.setError( "Reporter's name is required!" );
        }

        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        addDate.setText(date);

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH : mm : ss ");
        Date myDate = new Date();
        final String filename = timeStampFormat.format(myDate);
        addTime.setText(filename);

        if(etDimensions.getText().toString().length()==0&&addDate.getText().toString().length()==0&&addTime.getText().toString().length()!=10&&etRental.getText().toString().length()==0&&etReporter.getText().toString().length()==0){
            buttonSubmit.setEnabled(false);
        }else {
            buttonSubmit.setEnabled(true);
        }

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Setting up the required fields
                if(etDimensions.getText().toString().trim().equals("")){
                    etDimensions.setError("Dimensions are Required");
                }else if(etRental.getText().toString().equals("")){
                    etRental.setError("Monthly Rental is Required");
                }else if(etReporter.getText().toString().equals("")){
                    etReporter.setError("Name is Required");
                }else{
//                    Building confirmation Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Confirm Information");
                    builder.setIcon(R.drawable.bell);
                    builder.setMessage("Storage Type: "+spinnerStorageType.getSelectedItem().toString()+"\nDimensions: "+etDimensions.getText().toString()+"Sq. Meters\n"+ "Date: "+addDate.getText().toString()+ "\nTime: "+addTime.getText().toString()+"\nStorage Feature: "+spinnerFeatures.getSelectedItem().toString()
                        +"\nMonthly Rental: "+etRental.getText().toString()+"\nNotes: "+ etNotes.getText().toString()+"\nReporter's Name: "+etReporter.getText().toString());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Changes will be saved", Toast.LENGTH_SHORT).show();
                            makePost();
                            etDimensions.setText("");
                            addDate.setText(date);
                            addTime.setText(filename);
                            etRental.setText("");
                            etNotes.setText("");
                            etReporter.setText("");

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Changes will not be saved", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            }
        });
    }

    private void makePost(){
        String storage_type = spinnerStorageType.getSelectedItem().toString();
        String txtDimensions = etDimensions.getText().toString();
        double value = Double.parseDouble(txtDimensions);
        String date = addDate.getText().toString();
        String time = addTime.getText().toString();
        String store_features = spinnerFeatures.getSelectedItem().toString();
        String month_rental = etRental.getText().toString();
        int rent = Integer.parseInt(month_rental);
        String txtNotes = etNotes.getText().toString();
        String reporter_name = etReporter.getText().toString();

        if((!TextUtils.isEmpty(storage_type)) && (!TextUtils.isEmpty(txtDimensions)) && (!TextUtils.isEmpty(date)) && (!TextUtils.isEmpty(time)) && (!TextUtils.isEmpty(store_features))
            && (!TextUtils.isEmpty(month_rental)) && (!TextUtils.isEmpty(reporter_name))){

            String id = databaseStorage.push().getKey();
            PostAdd postAdd = new PostAdd(id, storage_type, value, date, time, store_features, rent, txtNotes, reporter_name);
            databaseStorage.child(id).setValue(postAdd);
            Toast.makeText(this, "Advertisement saved successfully", Toast.LENGTH_LONG).show();

        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, Locale.US);

        editCalender.setText(sdf.format(myCalendar.getTime()));
    }
}
