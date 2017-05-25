package fhtw.bsa2.gafert_steiner.ue4_restservice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public static String POSTURL;
    public static String GETURL;

    EditText postIP;
    EditText postPort;
    EditText postDirectory;

    EditText getIP;
    EditText getPort;
    EditText getDirectory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        postIP = (EditText) rootView.findViewById(R.id.postIP);
        postPort = (EditText) rootView.findViewById(R.id.postPort);
        postDirectory = (EditText) rootView.findViewById(R.id.postDirectory);

        getIP = (EditText) rootView.findViewById(R.id.getIP);
        getPort = (EditText) rootView.findViewById(R.id.getPort);
        getDirectory = (EditText) rootView.findViewById(R.id.getDirectory);

        OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
              */
                if (!hasFocus) {
                    makeIP();
                }
            }
        };

        postIP.setOnFocusChangeListener(focusChangeListener);
        postPort.setOnFocusChangeListener(focusChangeListener);
        postDirectory.setOnFocusChangeListener(focusChangeListener);
        getIP.setOnFocusChangeListener(focusChangeListener);
        getPort.setOnFocusChangeListener(focusChangeListener);
        getDirectory.setOnFocusChangeListener(focusChangeListener);

        makeIP();
        return rootView;
    }

    private void makeIP() {
        POSTURL = "http://" + postIP.getText().toString() + ":" + postPort.getText().toString() + postDirectory.getText().toString();
        GETURL = "http://" + getIP.getText().toString() + ":" + getPort.getText().toString() + getDirectory.getText().toString();
    }
}
