package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weight_datas")
public class WeightData {

    private long id;
    private String objectName;
    private String date;
    private String weight;

    public WeightData() {}

    public WeightData(long id, String objectName, String date, String weight) {
        this.id = id;
        this.setObjectName(objectName);
        this.setDate(date);
        this.setWeight(weight);
    }

    public long getId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
}
