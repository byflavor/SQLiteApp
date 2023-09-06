package com.example.a123003sqlapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A main activity to run the other parts of the code.
 *
 * @author 123003
 * @version 5/13/22
 */
public class MainActivity extends AppCompatActivity {

    TextView numberOfRecords;
    EditText name; EditText email;
    Spinner spinner;
    Button insert; Button select; Button update;  Button delete;
    SQLiteDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        spinner = findViewById(R.id.spinner);

        insert = findViewById(R.id.insert);
        select = findViewById(R.id.select);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        numberOfRecords = findViewById(R.id.numberOfRecords);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Select: ");
        categories.add("Doplhin");
        categories.add("Whale");
        categories.add("Shark");
        categories.add("Seahorse");
        categories.add("Plankton");

        //Putting Spinner data into ArrayAdapter
        ArrayAdapter dataAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        dbHandler = new SQLiteDBHandler(MainActivity.this);
        numberOfRecords.setText("Number of donations: "+ dbHandler.getNumElements());

        // below line is to add on click listener for our add course button.
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String selection = "" + spinner.getSelectedItemPosition();

                // ensure all fields are filled out.
                if (name1.isEmpty() || email1.isEmpty() || selection.equals("Select: ")) {
                    Toast.makeText(MainActivity.this, "Fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                boolean inserted = dbHandler.inserts(name1, email1, selection);

                if(inserted) {
                    // after adding the data we are displaying a toast message.
                    Toast.makeText(MainActivity.this, "Donation made.", Toast.LENGTH_SHORT).show();
                    numberOfRecords.setText("Number of donations: "+ dbHandler.getNumElements());
                }
                else {
                    Toast.makeText(MainActivity.this, "Can't add duplicate record", Toast.LENGTH_SHORT).show();
                }

                name.setText("");
                email.setText("");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below line is to get data from all edit text fields.
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String selection = "" + spinner.getSelectedItemPosition();

                // ensure all fields are filled out.
                if (name1.isEmpty() || email1.isEmpty() || selection.equals("Select: ")) {
                    Toast.makeText(MainActivity.this, "Fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                int updated = dbHandler.updates(name1, email1, selection);

                if(updated > 0) {
                    Toast.makeText(MainActivity.this, "Records updated.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No preexisting record", Toast.LENGTH_SHORT).show();
                }

                name.setText("");
                email.setText("");
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below line is to get data from all edit text fields.
                String name1 = name.getText().toString();

                String[] vals = dbHandler.selects(name1);
                if(vals.length == 0) {
                    Toast.makeText(MainActivity.this, "No record found for given name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    InfoDialogFragment.newInstance(vals[0], vals[1], Integer.parseInt(vals[2])).show(getSupportFragmentManager(), null);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int deleteDonation = dbHandler.deletes(name.getText().toString());
                if (deleteDonation > 0) {
                    Toast.makeText(MainActivity.this, "Deleted donation.", Toast.LENGTH_SHORT).show();
                    numberOfRecords.setText("Number of donations: "+ dbHandler.getNumElements());

                } else {
                    Toast.makeText(MainActivity.this, "Please delete from a pre-existing entry", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}