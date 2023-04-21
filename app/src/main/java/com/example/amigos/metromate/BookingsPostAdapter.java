package com.example.amigos.metromate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingsPostAdapter extends RecyclerView.Adapter<BookingsPostAdapter.ViewHolder> {

    private final ArrayList<BookingsObject> bookingsObjectArrayList;

    public BookingsPostAdapter(ArrayList<BookingsObject> bookingsObjectArrayList) {
        this.bookingsObjectArrayList = bookingsObjectArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingsObject bookingsObject = bookingsObjectArrayList.get(position);
        //Log.d("RidePostAdaptor", "onBindViewHolder: " + bookingsObject.getSource());
        holder.card_date.setText(bookingsObject.getDate().toString());
        holder.card_time.setText(bookingsObject.getTime().toString());
        holder.card_source.setText("SOURCE: "+bookingsObject.getSource());
        holder.card_destination.setText("DESTINATION: "+bookingsObject.getDestination());
        holder.card_fare.setText("FARE: ₹"+bookingsObject.getFare());

    }

    @Override
    public int getItemCount() {
        return bookingsObjectArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_date, card_time, card_source, card_destination, card_fare;
        public ViewHolder(@NonNull ViewGroup itemView){
            super(itemView);
            card_date = itemView.findViewById(R.id.card_date);
            card_time = itemView.findViewById(R.id.card_time);
            card_source = itemView.findViewById(R.id.card_source);
            card_destination = itemView.findViewById(R.id.card_destination);
            card_fare = itemView.findViewById(R.id.card_fare);
        }
    }
}
