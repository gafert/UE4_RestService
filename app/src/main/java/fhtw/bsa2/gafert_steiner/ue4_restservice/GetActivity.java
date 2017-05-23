package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

public class GetActivity extends AppCompatActivity {

    private final String URL = "http://10.43.0.237:8080/rest/items";
    private final String TAG = "GetActivity";
    private EditText editTextUrl;
    private Button getButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        // Get Layout elements
        editTextUrl = (EditText) findViewById(R.id.getUrlEditText);
        getButton = (Button) findViewById(R.id.getButton);
        listView = (ListView) findViewById(R.id.getListView);

        editTextUrl.setText(URL);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncGet mAsyncGet = new AsyncGet();
                mAsyncGet.execute(editTextUrl.getText().toString());
            }
        });
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
            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(GetActivity.this, android.R.layout.simple_list_item_1, listElements);
            listView.setAdapter(listViewAdapter);
        }
    }
}


