package webapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "data")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Data {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, name="objectName")
    private String objectName;

    @Column(nullable=false, name="date")
    private String date;

    public Data() {}

    public Data(String date, String objectName)
    {
        this.setDate(date);
        this.setObjectName(objectName);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectName() { return objectName; }
    public void setObjectName(String objectName) { this.objectName = objectName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }


    @Override
    public String toString() {
        return "[id = " + id
                + " - objectName = " + objectName
                + " - date = " + date;
    }

}
