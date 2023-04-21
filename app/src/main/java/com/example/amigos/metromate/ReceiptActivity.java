package com.example.amigos.metromate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptActivity extends AppCompatActivity {
    private TextView receipt_ticketNumber;
    private TextView receipt_journey;
    private TextView receipt_date;
    private TextView receipt_time;
    private TextView receipt_fare;
    private Button receipt_download;
    DatabaseReference databaseReference, userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        receipt_ticketNumber = findViewById(R.id.receipt_ticketNumber);
        receipt_download = findViewById(R.id.receipt_download);

        databaseReference = FirebaseDatabase.getInstance().getReference("BookingsPosts");

        Intent intent = getIntent();

        int random = intent.getIntExtra("random_number",0);
        String source = intent.getStringExtra("source1");
        String destination = intent.getStringExtra("destination1");
        int fare2 = intent.getIntExtra("fare2",0);

        receipt_date = findViewById(R.id.receipt_date);
        receipt_time = findViewById(R.id.receipt_time);
        receipt_journey = findViewById(R.id.receipt_journey);
        receipt_fare = findViewById(R.id.receipt_fare);

        Date currentDate = (Date) getIntent().getSerializableExtra("currentDate");
        Date currentTime = (Date) getIntent().getSerializableExtra("currentDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String dateString = dateFormat.format(currentDate);
        String timeString = timeFormat.format(currentTime);

        receipt_ticketNumber.setText("Ticket Number: "+random);
        receipt_date.setText("Date: " + dateString);
        receipt_time.setText("Time: "+ timeString);
        receipt_journey.setText("Journey: "+source+" to "+destination);
        receipt_fare.setText("Fare: "+fare2+" Rupees");

        BookingsObject bookingsObject = new BookingsObject(source, destination, fare2, dateString, timeString);
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(bookingsObject);

                /*card_date.setText("");
                card_time.setText("");
                card_source.setText("");
                card_destination.setText("");
                card_fare.setText("");*/
        Toast.makeText(ReceiptActivity.this, "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();

        receipt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptActivity.this, HomepageActivity.class);
                startActivity(intent);

                //generatePDFAndDownload();
            }
        });
    }
}