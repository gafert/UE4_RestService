package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodArrayAdapter;
import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodPressure;
import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

import static fhtw.bsa2.gafert_steiner.ue4_restservice.SettingsFragment.URL_PREFS;

public class GetFragment extends Fragment {

    private final String TAG = "GetFragment";

    Button getButton;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get, container, false);

        // Get Layout elements
        getButton = (Button) rootView.findViewById(R.id.getButton);
        listView = (ListView) rootView.findViewById(R.id.getListView);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start with IP specified in the settings
                SharedPreferences settings = getActivity().getSharedPreferences(URL_PREFS, 0);

                AsyncGet mAsyncGet = new AsyncGet();
                mAsyncGet.execute(settings.getString(SettingsFragment.GETURL_PREF, ""));
            }
        });

        return rootView;
    }

    private class AsyncGet extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            Rest mREST = new Rest();
            return mREST.getREST(strings[0]);
        }

        @Override
        protected void onPostExecute (String result){
            ArrayList mListElements = BloodpressureParser.parseJsonString(result);

            if (mListElements == null) {
                // If there are no results
                Snackbar snackbar = Snackbar
                        .make(getView(), "There is sadly nothing to show :(", Snackbar.LENGTH_SHORT);

                snackbar.show();

                // Add nothing to show
                mListElements = new ArrayList<>();
                mListElements.add("Nothing to show :(");
                ArrayAdapter<String> mListViewAdapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_get_listview_element_error, mListElements);
                listView.setAdapter(mListViewAdapter);
            } else {
                // Get the data formatted and add it to the list
                ArrayAdapter<BloodPressure> mListViewAdapter = new BloodArrayAdapter(getActivity(), R.layout.fragment_get_listview_element, mListElements);
                listView.setAdapter(mListViewAdapter);
            }
        }
    }
}


