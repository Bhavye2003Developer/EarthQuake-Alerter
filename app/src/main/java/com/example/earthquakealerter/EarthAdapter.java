package com.example.earthquakealerter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;

public class EarthAdapter extends ArrayAdapter<Earthquake_data_class> {

    public EarthAdapter(@NonNull Context context, ArrayList<Earthquake_data_class> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.quake_info, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        Earthquake_data_class currentNumberPosition = getItem(position);

        Double magnitude = new Double(Double.parseDouble(currentNumberPosition.GetMagnitude()));
        int magnitudeInt = magnitude.intValue();
//        Log.v("IMPORTANT", String.valueOf(magnitudeInt));

        if (magnitude==0 || magnitude==1){
            currentItemView.setBackgroundColor(Color.parseColor("#4A7BA7"));
        }
        else if (magnitude==2){
            currentItemView.setBackgroundColor(Color.parseColor("#04B4B3"));
        }
        else if (magnitude==3){
            currentItemView.setBackgroundColor(Color.parseColor("#10CAC9"));
        }
        else if (magnitude==4){
            currentItemView.setBackgroundColor(Color.parseColor("#F5A623"));
        }
        else if (magnitude==5){
            currentItemView.setBackgroundColor(Color.parseColor("#FF7D50"));
        }
        else if (magnitude==6){
            currentItemView.setBackgroundColor(Color.parseColor("#FC6644"));
        }
        else if (magnitude==7){
            currentItemView.setBackgroundColor(Color.parseColor("#E75F40"));
        }
        else if (magnitude==8){
            currentItemView.setBackgroundColor(Color.parseColor("#E13A20"));
        }
        else if (magnitude==9){
            currentItemView.setBackgroundColor(Color.parseColor("#D93218"));
        }
        else {
            currentItemView.setBackgroundColor(Color.parseColor("#C03823"));
        }



        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView);
        textView1.setText(currentNumberPosition.GetMagnitude());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView1);
        textView2.setText(currentNumberPosition.GetPlace());

        TextView textView3 = currentItemView.findViewById(R.id.textView2);
        textView3.setText(currentNumberPosition.GetDate());

        // then return the recyclable view
        return currentItemView;
    }
}
