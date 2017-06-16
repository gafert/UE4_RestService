package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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

        // Parse With gson
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BloodPressure>>() {
        }.getType();

        // Returns null if nothing
        ArrayList<BloodPressure> entries = gson.fromJson(jsonString, type);

        return entries;
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
