package fhtw.bsa2.gafert_steiner.ue4_restservice;


import android.content.SharedPreferences;
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

    // Static sharedSettings identifiers
    public static String IP_PREFS = "IpPrefs";
    public static String POSTIP_PREF = "postIp";
    public static String POSTDIRECTORY_PREF = "postDirectory";
    public static String POSTPORT_PREF = "postPort";
    public static String POSTURL_PREF = "postUrl";
    public static String GETIP_PREF = "getIP";
    public static String GETDIRECTORY_PREF = "getDirectory";
    public static String GETPORT_PREF = "getPort";
    public static String GETURL_PREF = "getUrl";
    private static String TAG = "SettingsFragment";
    EditText postIP;
    EditText postPort;
    EditText postDirectory;
    EditText getIP;
    EditText getPort;
    EditText getDirectory;
    private SharedPreferences settings;

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

        // Set input according to last preferences
        settings = getActivity().getSharedPreferences(IP_PREFS, 0);

        postIP.setText(settings.getString(POSTIP_PREF, null));
        postPort.setText(settings.getString(POSTPORT_PREF, null));
        postDirectory.setText(settings.getString(POSTDIRECTORY_PREF, null));

        getIP.setText(settings.getString(GETIP_PREF, null));
        getPort.setText(settings.getString(GETPORT_PREF, null));
        getDirectory.setText(settings.getString(GETDIRECTORY_PREF, null));

        // If user finished typing save the input in the preferences
        OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /*
             * When focus is lost check that the text field
             * has valid values.
             */
                if (!hasFocus) {
                    savePrefs();
            }
            }
        };

        postIP.setOnFocusChangeListener(focusChangeListener);
        postPort.setOnFocusChangeListener(focusChangeListener);
        postDirectory.setOnFocusChangeListener(focusChangeListener);
        getIP.setOnFocusChangeListener(focusChangeListener);
        getPort.setOnFocusChangeListener(focusChangeListener);
        getDirectory.setOnFocusChangeListener(focusChangeListener);

        return rootView;
    }

    private void savePrefs() {

        // Make url from input
        String mPostUrl = "http://" + postIP.getText().toString() + ":" + postPort.getText().toString() + postDirectory.getText().toString();
        String mGetUrl = "http://" + getIP.getText().toString() + ":" + getPort.getText().toString() + getDirectory.getText().toString();

        // Save preferences
        SharedPreferences.Editor editor = settings.edit();

        // Save Urls
        editor.putString(GETURL_PREF, mGetUrl);
        editor.putString(POSTURL_PREF, mPostUrl);

        // Save other input
        editor.putString(GETIP_PREF, getIP.getText().toString());
        editor.putString(GETPORT_PREF, getPort.getText().toString());
        editor.putString(GETDIRECTORY_PREF, getDirectory.getText().toString());

        editor.putString(POSTIP_PREF, postIP.getText().toString());
        editor.putString(POSTPORT_PREF, postPort.getText().toString());
        editor.putString(POSTDIRECTORY_PREF, postDirectory.getText().toString());

        // Commit the edits!
        // Apply commits in the background
        editor.apply();
    }
}
