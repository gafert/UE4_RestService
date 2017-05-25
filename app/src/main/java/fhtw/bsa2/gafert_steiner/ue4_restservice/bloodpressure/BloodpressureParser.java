package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BloodpressureParser {

    private static final String TAG = "BloodpressureParser";

    public static ArrayList parseJsonArray(String jsonArray){
        return new ArrayList();
    }

    public static ArrayList parseJsonString(String jsonString) {

        // Try Try and Try to get some values
        // If it cant be parsed as a array try object
        // If neither works do nothing
        JSONArray jsonArray;

        try {
            // Try to make json array
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException jsonArrayException) {
            Log.i(TAG, "parseJsonString: Response cannot be converted to Json Array");
            jsonArray = new JSONArray();
            JSONObject jsonTmpObj = null;
            try {
                // Try to make jsonObject
                jsonTmpObj = new JSONObject(jsonString);
            } catch (JSONException jsonObjectException) {
                Log.e(TAG, "parseJsonString: Response is cannot be converted to Json Object");
                // Neither could be made make empty jsonArray
                jsonArray = new JSONArray();
                jsonObjectException.printStackTrace();
            }
            jsonArray.put(jsonTmpObj);
        }

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
            e.printStackTrace();
            return null;
        }

    }

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
