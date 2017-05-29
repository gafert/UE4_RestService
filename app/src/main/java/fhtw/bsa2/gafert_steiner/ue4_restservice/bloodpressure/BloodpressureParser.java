package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BloodpressureParser {

    private static final String TAG = "BloodpressureParser";

    /**
     * Parses a json String to an Bloodpressure array
     *
     * @param jsonString
     * @return
     */
    public static ArrayList<BloodPressure> parseJsonString(String jsonString) {

        // Try Try and Try to get some values
        // If it cant be parsed as a array try object
        // If neither works set null
        JSONArray jsonArray;

        if (jsonString != null) {

            // Parse string to json Array
            try {
                // Try to make json array
                jsonArray = new JSONArray(jsonString);
            } catch (JSONException jsonArrayException) {
                // Json string cannot be directly converted to jsonArray
                // Try to make a json Object and add it to an array
                Log.i(TAG, "parseJsonString: Response cannot be converted to Json Array");
                jsonArray = new JSONArray();
                try {
                    // Try to make a jsonObject
                    // And try to add it to the array
                    JSONObject jsonTmpObj = new JSONObject(jsonString);
                    jsonArray.put(jsonTmpObj);
                } catch (JSONException jsonObjectException) {
                    Log.e(TAG, "parseJsonString: Response is cannot be converted to Json Object");
                    // Neither could be made make empty jsonArray
                    jsonObjectException.printStackTrace();
                }
            }

            // Parse json Array to bloodPressure array
            try {
                ArrayList<BloodPressure> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    list.add(new BloodPressure(jsonObject.getString("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("timestamp"),
                            Integer.parseInt(jsonObject.getString("diastolic_pressure")),
                            Integer.parseInt(jsonObject.getString("systolic_pressure")),
                            Integer.parseInt(jsonObject.getString("heart_rate")),
                            jsonObject.getString("pressure_unit"),
                            jsonObject.getString("heart_rate_unit")));
                }
                return list;
            } catch (JSONException e) {
                // Could not iterate and or parse to bloodpressure
                Log.e(TAG, "parseJsonString: No BloodPressure Array made");
                e.printStackTrace();
                return null;
            }
        } else return null;

    }

    /**
     * Makes a Json Object form a BloodPressure Object
     * @param bloodPressure
     * @return
     */
    public static String toJsonString(BloodPressure bloodPressure){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", bloodPressure.getId());
            jsonObj.put("name", bloodPressure.getName());
            jsonObj.put("timestamp", bloodPressure.getTimestamp());
            jsonObj.put("diastolic_pressure", bloodPressure.getDiastolic_pressure());
            jsonObj.put("systolic_pressure", bloodPressure.getSystolic_pressure());
            jsonObj.put("heart_rate", bloodPressure.getHeart_rate());
            jsonObj.put("pressure_unit", bloodPressure.getPressure_unit());
            jsonObj.put("heart_rate_unit", bloodPressure.getHeart_rate_unit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj.toString();
    }
}
