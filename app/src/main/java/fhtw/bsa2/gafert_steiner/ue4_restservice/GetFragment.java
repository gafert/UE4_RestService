package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

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
                AsyncGet mAsyncGet = new AsyncGet();
                mAsyncGet.execute(SettingsFragment.GETURL);
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
            ArrayList<String> listElements;

            if (result == null) {
                // If there are no results
                listElements = new ArrayList<String>();
                listElements.add("Sadly no results\nEither network wise or mine...\n\nSorry :(");
            } else {
                // Get the data formatted and add it to the list
                listElements = BloodpressureParser.parseJsonString(result);
            }

            // Make new Adapter
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listElements);
            listView.setAdapter(listViewAdapter);
        }
    }
}


