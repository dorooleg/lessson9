package com.csc.weatherapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class DescriptionCityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description_city, container, false);
        final TextView tv = (TextView)view.findViewById(R.id.full_description);
        view.findViewById(R.id.description_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherTask task = new WeatherTask(getActivity().getContentResolver());
                task.execute();
            }
        });
        Intent intent = getActivity().getIntent();
        Cursor cursor = null;
        try {


            String city_id = intent.getStringExtra("CITY_ID");
            if (city_id == null) {
                city_id = String.valueOf(getArguments().getInt("CITY_ID"));
            } else {
                final String tmp_city_id = city_id;
                Spinner spinner = (Spinner)view.findViewById(R.id.spinner_description);
                spinner.setVisibility(View.VISIBLE);
                cursor = getActivity().getContentResolver().query(ListCitiesFragment.ENTRIES_URI, null, null, null, null);
                final String cities[] = { "Moscow", "Saint Petersburg", "Belgorod" };
                final Integer codes[] = { 5601538, 498817,  578072 };
                final String[] data = new String[cursor.getCount()];
                for (int i = 0; cursor.moveToNext(); i++) {
                    Integer id = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_ID));
                    int j = 0;
                    while (!codes[j].equals(id)) j++;
                    data[i] = cities[j];

                }
                cursor.close();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                spinner.setAdapter(adapter);
                int j = 0;
                while (!codes[j].equals(new Integer(city_id))) j++;
                String c = cities[j];
                j = 0;
                while (!data[j].equals(c)) j++;
                spinner.setSelection(j);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Cursor cursor = null;
                        try {
                            int j = 0;
                            while (!cities[j].equals(data[position])) j++;
                            cursor = getActivity().getContentResolver().query(ListCitiesFragment.ENTRIES_URI, null, FeedsTable.COLUMN_ID + " = " + codes[j], null, null);
                            cursor.moveToNext();
                            tv.setText("COD = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_COD)) +
                                    " \nDATE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_DATE)) +
                                    " \nTEMP = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP)) +
                                    " \nPRESSURE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_PRESSURE)) +
                                    " \nTEMP_MAX = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MAX)) +
                                    " \nTEMP_MIN = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MIN)) +
                                    " \nWIND_DEG = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_DEG)) +
                                    " \nWIND_SPEED = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_SPEED)));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            cursor = getActivity().getContentResolver().query(ListCitiesFragment.ENTRIES_URI, null, FeedsTable.COLUMN_ID + " = " + city_id, null, null);
            cursor.moveToNext();
            tv.setText("COD = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_COD)) +
                    " \nDATE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_DATE)) +
                    " \nTEMP = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP)) +
                    " \nPRESSURE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_PRESSURE)) +
                    " \nTEMP_MAX = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MAX)) +
                    " \nTEMP_MIN = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MIN)) +
                    " \nWIND_DEG = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_DEG)) +
                    " \nWIND_SPEED = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_SPEED)));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return view;
    }
}
