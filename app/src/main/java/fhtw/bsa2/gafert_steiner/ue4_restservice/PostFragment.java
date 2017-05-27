package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodPressure;
import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

import static fhtw.bsa2.gafert_steiner.ue4_restservice.SettingsFragment.URL_PREFS;

public class PostFragment extends Fragment {

    EditText postName;
    EditText postID;
    EditText postDia;
    EditText postSys;
    EditText postHeartRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        Button postButton = (Button) rootView.findViewById(R.id.postButton);
        postID = (EditText) rootView.findViewById(R.id.postEditTextID);
        postName = (EditText) rootView.findViewById(R.id.postEditTextName);
        postDia = (EditText) rootView.findViewById(R.id.postEditTextDia);
        postSys = (EditText) rootView.findViewById(R.id.postEditTextSys);
        postHeartRate = (EditText) rootView.findViewById(R.id.postEditTextHeartRate);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make new BloodPressure Object and populate it
                BloodPressure mBloodPressure = new BloodPressure();
                mBloodPressure.setId(postID.getText().toString());
                mBloodPressure.setName(postName.getText().toString());
                mBloodPressure.setDiastolic_pressure(Integer.parseInt(postDia.getText().toString()));
                mBloodPressure.setSystolic_pressure(Integer.parseInt(postSys.getText().toString()));
                mBloodPressure.setPressure_unit("mm Hg");
                mBloodPressure.setHeart_rate(Integer.parseInt(postHeartRate.getText().toString()));
                mBloodPressure.setHeart_rate_unit("bpm");

                // Get IP from settings and start sending
                SharedPreferences settings = getActivity().getSharedPreferences(URL_PREFS, 0);
                AsyncPost mAsyncGet = new AsyncPost();
                mAsyncGet.execute(settings.getString(SettingsFragment.POSTURL_PREF, null), mBloodPressure);
            }
        });
        return rootView;
    }

    private class AsyncPost extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... object) {
            // Start REST and parse to json
            String url = (String) object[0];
            BloodPressure bloodpressure = (BloodPressure) object[1];

            Rest mRest = new Rest();
            return mRest.postREST(url, BloodpressureParser.toJsonString(bloodpressure));
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(getActivity(), "Could not send", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Sent Data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
