package fhtw.bsa2.gafert_steiner.ue4_restservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getButton = (Button)findViewById(R.id.getButton);
        Button postButton = (Button) findViewById(R.id.postButton);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getActivity = new Intent(MainActivity.this, GetActivity.class);
                startActivity(getActivity);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getActivity = new Intent(MainActivity.this, PostActivity.class);
                startActivity(getActivity);
            }
        });

    }
}