package webapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "object_infos_datas")
public class ObjectInfosData {

    @Id
    @Column
    private long id;

    @Column
    private String objectName;
    
    @Column
    private String status;
    
    @Column
    private String batteryLevel;

    public ObjectInfosData() {}

    public ObjectInfosData(long id, String objectName, String status, String batteryLevel) {
        this.setId(id);
        this.setObjectName(objectName);
        this.setStatus(status);
        this.setBatteryLevel(batteryLevel);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getObjectName() { return objectName; }
    public void setObjectName(String objectName) { this.objectName = objectName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(String batteryLevel) { this.batteryLevel = batteryLevel; }

}
