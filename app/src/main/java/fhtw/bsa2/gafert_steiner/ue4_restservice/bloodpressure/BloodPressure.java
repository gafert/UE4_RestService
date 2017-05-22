package fhtw.bsa2.gafert_steiner.ue4_restservice.bloodpressure;

/**
 * Created by hammer
 */
public class BloodPressure {

    private String id;
    private String name;
    private String timestamp;
    private int diastolic_pressure;
    private int systolic_pressure;
    private int heart_rate;
    private String pressure_unit;
    private String heart_rate_unit;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getDiastolic_pressure() {
        return diastolic_pressure;
    }

    public void setDiastolic_pressure(int diastolic_pressure) {
        this.diastolic_pressure = diastolic_pressure;
    }

    public int getSystolic_pressure() {
        return systolic_pressure;
    }

    public void setSystolic_pressure(int systolic_pressure) {
        this.systolic_pressure = systolic_pressure;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getPressure_unit() {
        return pressure_unit;
    }

    public void setPressure_unit(String pressure_unit) {
        this.pressure_unit = pressure_unit;
    }

    public String getHeart_rate_unit() {
        return heart_rate_unit;
    }

    public void setHeart_rate_unit(String heart_rate_unit) {
        this.heart_rate_unit = heart_rate_unit;
    }

    @Override
    public String toString() {
        return "BloodPressure{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", diastolic_pressure=" + diastolic_pressure +
                ", systolic_pressure=" + systolic_pressure +
                ", heart_rate=" + heart_rate +
                ", pressure_unit='" + pressure_unit + '\'' +
                ", heart_rate_unit='" + heart_rate_unit + '\'' +
                '}';
    }
}