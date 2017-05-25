package fhtw.bsa2.gafert_steiner.ue4_restservice;

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
                BloodPressure bloodPressure = new BloodPressure();
                bloodPressure.setId(postID.getText().toString());
                bloodPressure.setName(postName.getText().toString());
                bloodPressure.setDiastolic_pressure(Integer.parseInt(postDia.getText().toString()));
                bloodPressure.setSystolic_pressure(Integer.parseInt(postSys.getText().toString()));
                bloodPressure.setPressure_unit("mm Hg");
                bloodPressure.setHeart_rate(Integer.parseInt(postHeartRate.getText().toString()));
                bloodPressure.setHeart_rate_unit("bpm");

                AsyncPost mAsyncGet = new AsyncPost();
                mAsyncGet.execute(SettingsFragment.POSTURL, bloodPressure);
            }
        });
        return rootView;
    }

    private class AsyncPost extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... object) {
            Rest mRest = new Rest();

            String url = (String) object[0];
            BloodPressure bloodpressure = (BloodPressure) object[1];

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
