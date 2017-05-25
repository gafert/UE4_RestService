package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fhtw.bsa2.gafert_steiner.ue4_restservice.R;

/**
 * Created by michi on 25.05.17.
 */

public class BloodArrayAdapter extends ArrayAdapter<BloodPressure> {

    private Context context;
    private List<BloodPressure> bloodPressureList;
    private int resource;

    public BloodArrayAdapter(Context context, int resource, ArrayList<BloodPressure> objects) {
        super(context, resource, objects);

        this.context = context;
        this.bloodPressureList = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BloodPressure mBloodPressure = bloodPressureList.get(position);
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(resource, null);

        TextView mId = (TextView) view.findViewById(R.id.id);
        TextView mName = (TextView) view.findViewById(R.id.name);
        TextView mTimestamp = (TextView) view.findViewById(R.id.timestamp);
        TextView mSys = (TextView) view.findViewById(R.id.sys);
        TextView mDia = (TextView) view.findViewById(R.id.dia);
        TextView mHeartRate = (TextView) view.findViewById(R.id.heartRate);

        mId.setText(mBloodPressure.getId());
        mName.setText(mBloodPressure.getName());
        mTimestamp.setText(mBloodPressure.getTimestamp());
        mSys.setText(mBloodPressure.getSystolic_pressure() + " " + mBloodPressure.getPressure_unit());
        mDia.setText(mBloodPressure.getDiastolic_pressure() + " " + mBloodPressure.getPressure_unit());
        mHeartRate.setText(mBloodPressure.getHeart_rate() + " " + mBloodPressure.getHeart_rate_unit());

        return view;
    }
}
