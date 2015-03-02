package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "body_fat_datas")
public class BodyFatData extends Data {

    private String bodyFat;



    public BodyFatData() {}

    public BodyFatData(String objectName, String date, String bodyFat) {
        super(date, objectName);
        this.setBodyFat(bodyFat);
    }



    public String getBodyFat() {
        return bodyFat;
    }
    public void setBodyFat(String bodyFat) {
        this.bodyFat = bodyFat;
    }
}
