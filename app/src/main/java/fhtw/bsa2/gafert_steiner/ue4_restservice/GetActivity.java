package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure.BloodpressureParser;
import fhtw.bsa2.gafert_steiner.ue4_restservice.restservices.Rest;

public class GetActivity extends AppCompatActivity {

    EditText editTextUrl;
    Button getButton;
    ListView listView;
    ArrayAdapter<String> listViewAdapter;
    List<String> listElements;

    private final String URL = "http://10.43.0.237:8080/rest/items";
    private final String TAG = "GetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        // Get Layout elements
        editTextUrl = (EditText)findViewById(R.id.editTextUrl);
        getButton = (Button)findViewById(R.id.buttonGet);
        listView = (ListView)findViewById(R.id.listView);

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
            String result = "NO RESULT";

            return mREST.getREST(strings[0]);
        }

        @Override
        protected void onPostExecute (String result){
            // Get the data formatted and add it to the list
            listElements = BloodpressureParser.parseJsonString(result);

            listViewAdapter = new ArrayAdapter<String>(GetActivity.this, android.R.layout.simple_list_item_1, listElements);
            listView.setAdapter(listViewAdapter);
        }
    }
}


