package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodPressure;
import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

import static fhtw.bsa2.gafert_steiner.ue4_restservice.SettingsFragment.URL_PREFS;

public class ChartFragment extends Fragment {

    LineChart chart;
    List<ILineDataSet> dataSets;
    LineDataSet systolicDataSet;
    LineDataSet diastolicDataSet;
    LineDataSet heartRateDataSet;
    LineData lineData;

    SharedPreferences settings;

    SwipeRefreshLayout swipeRefreshLayout;

    Switch diaSwitch;
    Switch sysSwitch;
    Switch heartRateSwitch;

    TextView mmHgDescription;
    TextView bpmDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        // Get Layout
        chart = new LineChart(getActivity());
        FrameLayout chartLayout = (FrameLayout) rootView.findViewById(R.id.chart);
        chartLayout.addView(chart);
        heartRateSwitch = (Switch) rootView.findViewById(R.id.heartRate);
        sysSwitch = (Switch) rootView.findViewById(R.id.sys);
        diaSwitch = (Switch) rootView.findViewById(R.id.dia);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        mmHgDescription = (TextView) rootView.findViewById(R.id.mmHgDescription);
        bpmDescription = (TextView) rootView.findViewById(R.id.bpmDescription);

        // Start chart creation with get settings
        settings = getActivity().getSharedPreferences(URL_PREFS, 0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ChartFragment.AsyncGet mAsyncGet = new ChartFragment.AsyncGet();
                mAsyncGet.execute(settings.getString(SettingsFragment.GETURL_PREF, ""));
            }
        });

        heartRateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dataSets != null) {
                    if (isChecked) {
                        dataSets.add(heartRateDataSet);
                        enableHeartRateAxis(true);
                    } else {
                        dataSets.remove(heartRateDataSet);
                        enableHeartRateAxis(false);
                    }
                    refreshChart();
                } else {
                    heartRateSwitch.setChecked(false);
                }
            }
        });

        sysSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dataSets != null) {
                    if (isChecked) {
                        dataSets.add(systolicDataSet);
                        enablePressureAxis(true);
                    } else {
                        dataSets.remove(systolicDataSet);
                        enablePressureAxis(false);
                    }
                    refreshChart();
                } else {
                    sysSwitch.setChecked(false);
                }
            }
        });

        diaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dataSets != null) {
                    if (isChecked) {
                        dataSets.add(diastolicDataSet);
                        enablePressureAxis(true);
                    } else {
                        dataSets.remove(diastolicDataSet);
                        enablePressureAxis(false);
                    }
                    refreshChart();
                } else {
                    diaSwitch.setChecked(false);
                }
            }
        });

        // Autostart
        ChartFragment.AsyncGet mAsyncGet = new ChartFragment.AsyncGet();
        mAsyncGet.execute(settings.getString(SettingsFragment.GETURL_PREF, ""));

        return rootView;
    }

    private void enableHeartRateAxis(Boolean on) {
        if (!on) {
            bpmDescription.setVisibility(View.INVISIBLE);
        } else {
            bpmDescription.setVisibility(View.VISIBLE);
        }
    }

    private void enablePressureAxis(Boolean on) {
        if (!on) {
            // Check both switches before disabling
            if (!sysSwitch.isChecked() && !diaSwitch.isChecked()) {
                mmHgDescription.setVisibility(View.INVISIBLE);
            }
        } else {
            mmHgDescription.setVisibility(View.VISIBLE);
        }
    }

    private void setupChart(ArrayList<BloodPressure> mListElements) {

        List<Entry> heartRateEntries = new ArrayList<>();
        List<Entry> diastolicEntries = new ArrayList<>();
        List<Entry> systolicEntries = new ArrayList<>();
        List<String> xValue = new ArrayList<>();

        // Convert bloodPressure to entry lists
        int i = 0;
        for (BloodPressure mBloodPressure : mListElements) {
            heartRateEntries.add(new Entry(mBloodPressure.getHeart_rate(), i));
            diastolicEntries.add(new Entry(mBloodPressure.getDiastolic_pressure(), i));
            systolicEntries.add(new Entry(mBloodPressure.getSystolic_pressure(), i));
            xValue.add(mBloodPressure.getTimestamp());
            i++;
        }

        // Make dataSet lines
        heartRateDataSet = new LineDataSet(heartRateEntries, "Heart Rate");
        heartRateDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        heartRateDataSet.setDrawCircles(false);
        heartRateDataSet.setColor(Color.RED);

        diastolicDataSet = new LineDataSet(diastolicEntries, "Diastolic Pressure");
        diastolicDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        diastolicDataSet.setDrawCircles(false);
        diastolicDataSet.setColor(Color.BLUE);

        systolicDataSet = new LineDataSet(systolicEntries, "Systolic Pressure");
        systolicDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        systolicDataSet.setDrawCircles(false);
        systolicDataSet.setColor(Color.GREEN);

        // Add all lines to a List which are set
        dataSets = new ArrayList<ILineDataSet>();
        if (heartRateSwitch.isChecked()) {
            dataSets.add(heartRateDataSet);
        }
        if (diaSwitch.isChecked()) {
            dataSets.add(diastolicDataSet);
        }
        if (sysSwitch.isChecked()) {
            dataSets.add(systolicDataSet);
        }

        lineData = new LineData(xValue, dataSets);

        refreshChart();
    }

    private void refreshChart() {

        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();

        // Add the list to a LineData
        //data.setValueTextSize(12f);
        lineData.setDrawValues(false);

        // Add the lines to the chart
        chart.setData(lineData);

        // Make the chart interactive
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // Disables marker
        chart.getData().setHighlightEnabled(false);

        // Disables Legend
        chart.getLegend().setEnabled(false);

        // Disables X Axis
        chart.getXAxis().setEnabled(false);

        // Remove Description
        chart.setDescription("");

        // Color axis
        chart.getAxisRight().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextColor(Color.WHITE);

        chart.invalidate();
    }

    private class AsyncGet extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Rest mREST = new Rest();
            return mREST.getREST(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            swipeRefreshLayout.setRefreshing(false);
            if (result == null) {
                // If there are no results
                Toast.makeText(getActivity(), "No Server Update", Toast.LENGTH_SHORT).show();
            } else {
                // Get the data formatted and add it to the list
                ArrayList<BloodPressure> mListElements = BloodpressureParser.parseJsonString(result);

                if (mListElements != null) {
                    setupChart(mListElements);
                }
            }
        }
    }


}
