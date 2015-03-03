package webapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "heart_rate_datas")
public class HeartRateData extends Data {

    @Column
    private String heartRate;

    public HeartRateData() {}

    public HeartRateData(String date, String objectName, String heartRate) {
        super(date, objectName);
        this.setHeartRate(heartRate);
    }

    public String getHeartRate() { return heartRate; }
    public void setHeartRate(String heartRate) { this.heartRate = heartRate; }



    @Override
    public String toString() {
        return super.toString() + ", \"heartRate\" : " + heartRate + "}";
    }
    
}
