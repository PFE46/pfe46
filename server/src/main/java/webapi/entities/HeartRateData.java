package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "heart_rate_datas")
public class HeartRateData extends Data {

    private String heartRate;



    public HeartRateData() {}

    public HeartRateData(String date, String objectName, String heartRate) {
        super(date, objectName);
        this.setHeartRate(heartRate);
    }



    public String getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}
