package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodPressure;
import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

public class PostActivity extends AppCompatActivity {

    EditText postUrl;
    EditText postName;
    EditText postID;
    EditText postDia;
    EditText postSys;
    EditText postHeartRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button postButton = (Button) findViewById(R.id.postButton);
        postUrl = (EditText) findViewById(R.id.postUrlEditText);
        postID = (EditText) findViewById(R.id.postEditTextID);
        postName = (EditText) findViewById(R.id.postEditTextName);
        postDia = (EditText) findViewById(R.id.postEditTextDia);
        postSys = (EditText) findViewById(R.id.postEditTextSys);
        postHeartRate = (EditText) findViewById(R.id.postEditTextHeartRate);

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
                mAsyncGet.execute(postUrl.getText().toString(), bloodPressure);
            }
        });
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
                Toast.makeText(PostActivity.this, "Could not send", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PostActivity.this, "Sent Data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
