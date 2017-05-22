package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by michi on 22.05.17.
 */

public class BloodpressureParser {

    public static ArrayList parseJsonArray(String jsonArray){
        return new ArrayList();
    }

    public static ArrayList parseJsonString(String jsonString) {

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            ArrayList<String> list = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                list.add(
                        "ID: " + jsonObject.getString("id") + "\n" +
                                "Name: " + jsonObject.getString("name") + "\n" +
                                "Timestamp: " + jsonObject.getString("timestamp") + "\n" +
                                "Diastolic: " + jsonObject.getString("diastolic_pressure") + jsonObject.getString("pressure_unit") + "\n" +
                                "Systolic: " + jsonObject.getString("systolic_pressure") + jsonObject.getString("pressure_unit") + "\n" +
                                "Heart Rate: " + jsonObject.getString("heart_rate") + jsonObject.getString("heart_rate_unit")
                );

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
