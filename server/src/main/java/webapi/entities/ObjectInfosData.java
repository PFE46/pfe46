package webapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "object_infos_datas")
public class ObjectInfosData {

    private long id;
    private String objectName;
    private String status;
    private String batteryLevel;

    public ObjectInfosData() {}

    public ObjectInfosData(long id, String objectName, String status, String batteryLevel) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }
    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
