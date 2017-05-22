package fhtw.bsa2.gafert_steiner.ue4_restservice.restservices;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michi on 22.05.17.
 */

public class Rest {

    /*
     *  GET
     *  - Set-up of HTTP connection, configure connection, close connection
     *  - Read from the server via the connection
     *      - Using BufferedReader, InputStreamReader (Java I/O)
     *
     *  POST
     *  - Set-up of HTTP connection, configure connection, close connection
     *  - Write to the server via the connection
     *      - Using BufferedOutputStream (OutputStream) for write
     *      - Using BufferedReader, InputStreamReader (Java I/O) to get reply of action success
     */

    final String TAG = "Rest";

    public String getREST(String url) {
        String result = "NO RESULT";

        try {
            // openConnection and set-up connection
            URL mUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "BSA");
            int responseCode = con.getResponseCode();
            // Handling of response code should be added here …
            Log.d(TAG, "HTTP Response: " + responseCode); // 200 OK

            // Prepare connection for reading a stream and read in while
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;

            while ((output = br.readLine()) != null) {
                result = output;
            }

            con.disconnect();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public String postREST(String url, String jsonString) {
        String result = "NO RESULT";

        try {
            // openConnection
            URL mUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) mUrl.openConnection();
            con.setDoOutput(true); con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("User-Agent", "BSA");

            // Send POST output
            OutputStream printout = new BufferedOutputStream(con.getOutputStream());
            printout.write(jsonString.getBytes());
            printout.flush(); printout.close();

            // Get reply from Vital Server
            InputStream inputStream = con.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null){
                buffer.append(inputLine + "\n");
            }
            result = buffer.toString();
            con.disconnect();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }
}

