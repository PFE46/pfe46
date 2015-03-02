package webapi;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "datas")
public class Data {

    private long id;
    private String objectName;
    private String date;
    private String weight;

    public Data() {}

    public Data(long id, String objectName, String date, String weight) {
        this.id = id;
        this.objectName = objectName;
        this.date = date;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }
    public String getDate() {
        return date;
    }
    public String getWeight() {
        return weight;
    }


}
