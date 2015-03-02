package webapi.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class Data {

    private String date;
    private String objectName;


    public Data() {}
    public Data(String date, String objectName) {
        this.setDate(date);
        this.setObjectName(objectName);
    }



    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
