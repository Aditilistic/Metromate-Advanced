package com.example.amigos.metromate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class StationsActivity extends AppCompatActivity {

    ArrayList<CardModel> arrCard = new ArrayList<CardModel>();
    RecyclerView route_recycler;

    private Button stations_back_button;
    private TextView stations_interchange;
    private TextView stations_time;
    private TextView stations_fare;
    private TextView stations_journey;
    private TextView stations_path_number;
    private Button stations_proceed_button;
    /*DatabaseReference databaseReference, userDatabaseReference;*/

    private int randomNum;

    @SuppressLint({"SetText", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);

        route_recycler = findViewById(R.id.route_recycler);

        stations_interchange = findViewById(R.id.stations_interchange);
        stations_time = findViewById(R.id.stations_time);
        stations_fare = findViewById(R.id.stations_fare);
        stations_path_number = findViewById(R.id.stations_path_number);
        stations_journey = findViewById(R.id.stations_journey);

        route_recycler.setLayoutManager(new LinearLayoutManager(StationsActivity.this));

        /*databaseReference = FirebaseDatabase.getInstance().getReference("RidePosts");*/

        RecyclerCardAdapter adapter = new RecyclerCardAdapter(StationsActivity.this, arrCard);
        route_recycler.setAdapter(adapter);

        Intent intent = getIntent();

        int responseCode = intent.getIntExtra("responseCode", -1);
        String source = intent.getStringExtra("source");
        String destination = intent.getStringExtra("destination");

        //CHANGED SOURCE STATIONS TO CAPITALIZE FORMAT
        String[] sources = source.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word: sources) {
            sb.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
            sb.append(" ");
        }
        String final_source = sb.toString().trim();

        //CHANGED DESTINATION STATIONS TO CAPITALIZE FORMAT
        String[] destinations = destination.split(" ");
        StringBuilder sb2 = new StringBuilder();
        for (String word: destinations) {
            sb2.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
            sb2.append(" ");
        }
        String final_destination = sb2.toString().trim();

        stations_journey.setText(final_source+" to "+final_destination);

        //GETTING ALL VALUES OF VALUES FROM API HERE
        ArrayList<String> line1 = intent.getStringArrayListExtra("Line1");
        ArrayList<String> line2 = intent.getStringArrayListExtra("Line2");
        ArrayList<String> interchange = intent.getStringArrayListExtra("Interchange");
        ArrayList<String> lineEnds = intent.getStringArrayListExtra("LineEnds");
        ArrayList<String> path = intent.getStringArrayListExtra("Path");
        String time = intent.getStringExtra("Time");
        int fare = intent.getIntExtra("fare",0);
        int final_time = (int) Math.ceil(Double.parseDouble(time));
        int interchange_length = interchange.size();

        String first_station = path.get(0);
        String first_line = line1.get(0);

        ArrayList<String> lines = new ArrayList<String>();
        lines.addAll(line1);
        lines.addAll(line2);

        //REMOVING DUPLICATES FROM LINE1 & LINE2
        Set<String> set = new LinkedHashSet<>(lines);
        lines.clear();
        lines.addAll(set);

        //CAPITALIZING LINES (COLOR)
        for (int i = 0; i < lines.size(); i++) {
            String str = lines.get(i);
            String capitalized = str.substring(0, 1).toUpperCase() + str.substring(1);
            lines.set(i, capitalized);
        }


        CardModel model = new CardModel(first_station,first_line,"",0);

        ArrayList<String> new_path = new ArrayList<String>();
        new_path.add(final_source);
        new_path.addAll(path);

        int pathValue = new_path.size();

        String letsShow;
        int count = 0;
        for (int i=1; i<pathValue;i++) {
            if (interchange.contains(new_path.get(i-1))) {
                count++;
            }
            if (interchange.contains(path.get(i-1))) {
                letsShow="Interchange";
            } else {
                letsShow="";
            }
            String new_station = new_path.get(i);
            String[] words = new_station.split(" ");

            StringBuilder capitalizedSentence = new StringBuilder();

            for (String word : words) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
                capitalizedSentence.append(capitalizedWord).append(" ");
            }

            String finalSentence = capitalizedSentence.toString().trim();

            arrCard.add(new CardModel(finalSentence,lines.get(count), letsShow,0));
        }

        stations_interchange.setText(String.valueOf(interchange_length));
        stations_time.setText(String.valueOf(final_time));
        stations_fare.setText("₹"+String.valueOf(fare));
        stations_path_number.setText(String.valueOf(pathValue));

        /*Checkout.preload(getApplicationContext());
        checkout.setKeyID("rzp_test_HAgUqUnW8vmvXm");*/


        stations_proceed_button = findViewById(R.id.stations_proceed_button);
        stations_proceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* BookingsObject bookingsObject = new BookingsObject(time,time, fare, source, destination);
                String key = databaseReference.push().getKey();
                databaseReference.child(key).setValue(bookingsObject);

                *//*card_date.setText("");
                card_time.setText("");
                card_source.setText("");
                card_destination.setText("");
                card_fare.setText("");*//*
                Toast.makeText(StationsActivity.this, "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();*/

                Intent intent = new Intent(StationsActivity.this, ReceiptActivity.class);

                intent.putExtra("fare2",fare);
                intent.putExtra("source1",final_source);
                intent.putExtra("destination1",final_destination);

                Random rand = new Random();
                int randomNum = rand.nextInt(90000000)+10000000;
                intent.putExtra("random_number",randomNum);

                Date currentDate = new Date();
                intent.putExtra("currentDate",currentDate);

                startActivity(intent);
            }
        });



  /*stations_back_button = findViewById(R.id.stations_back_button);
        stations_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationsActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });*/

    }
}
