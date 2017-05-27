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
import android.widget.Switch;

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

import static fhtw.bsa2.gafert_steiner.ue4_restservice.SettingsFragment.IP_PREFS;

public class ChartFragment extends Fragment {

    LineChart chart;
    List<ILineDataSet> dataSets;
    LineDataSet systolicDataSet;
    LineDataSet diastolicDataSet;
    LineDataSet heartRateDataSet;
    LineData lineData;

    SharedPreferences settings;

    SwipeRefreshLayout swipeRefreshLayout;

    Boolean heartRateOn = false;
    Boolean sysOn = false;
    Boolean diaOn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        // Get the chart Element
        chart = (LineChart) rootView.findViewById(R.id.chart);

        // Start chart creation with get settings
        settings = getActivity().getSharedPreferences(IP_PREFS, 0);

        ChartFragment.AsyncGet mAsyncGet = new ChartFragment.AsyncGet();
        mAsyncGet.execute(settings.getString(SettingsFragment.GETURL_PREF, ""));

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ChartFragment.AsyncGet mAsyncGet = new ChartFragment.AsyncGet();
                mAsyncGet.execute(settings.getString(SettingsFragment.GETURL_PREF, ""));
            }
        });

        ((Switch) rootView.findViewById(R.id.heartRate)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                heartRateOn = isChecked;
                if (isChecked) {
                    dataSets.add(heartRateDataSet);
                } else {
                    dataSets.remove(heartRateDataSet);
                }
                updateChart();
            }
        });

        ((Switch) rootView.findViewById(R.id.sys)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sysOn = isChecked;
                if (isChecked) {
                    dataSets.add(systolicDataSet);
                } else {
                    dataSets.remove(systolicDataSet);
                }
                updateChart();
            }
        });

        ((Switch) rootView.findViewById(R.id.dia)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diaOn = isChecked;
                if (isChecked) {
                    dataSets.add(diastolicDataSet);
                } else {
                    dataSets.remove(diastolicDataSet);
                }
                updateChart();
            }
        });

        return rootView;
    }

    private void updateChart() {
        lineData.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
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

        // Add all lines to a List
        dataSets = new ArrayList<ILineDataSet>();
        if (heartRateOn) {
            dataSets.add(heartRateDataSet);
        }
        if (diaOn) {
            dataSets.add(diastolicDataSet);
        }
        if (sysOn) {
            dataSets.add(systolicDataSet);
        }

        // Add the list to a LineData
        lineData = new LineData(xValue, dataSets);
        //data.setValueTextSize(12f);
        lineData.setValueTextColor(Color.WHITE);
        lineData.setDrawValues(false);

        // Add the lines to the chart
        chart.setData(lineData);

        // Make the chart interactive
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        styleChart();

        chart.invalidate();
    }

    private void styleChart() {
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
        chart.getAxisRight().setDrawGridLines(true);
        //chart.getAxisRight().setEnabled(false);

        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setDrawGridLines(true);
        //chart.getAxisLeft().setEnabled(false);
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
