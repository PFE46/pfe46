package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "body_fat_datas")
public class BodyFatData {

    private long id;
    private String date;
    private String bodyFat;

    public BodyFatData() {}

    public BodyFatData(long id, String objectName, String date, String bodyFat) {
        this.id = id;
        this.setDate(date);
        this.setBodyFat(bodyFat);
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

    public String getBodyFat() {
        return bodyFat;
    }
    public void setBodyFat(String bodyFat) {
        this.bodyFat = bodyFat;
    }
}
