package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "heart_rate_datas")
public class HeartRateData {

    private long id;
    private String date;
    private String heartRate;

    public HeartRateData() {}

    public HeartRateData(long id, String date, String heartRate) {
        this.id = id;
        this.setDate(date);
        this.setHeartRate(heartRate);
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}
