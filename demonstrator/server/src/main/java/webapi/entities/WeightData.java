package webapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "weight_datas")
public class WeightData extends Data {

    @Column
    private String weight;

    public WeightData() {}

    public WeightData(String date, String objectName, String weight) {
        super(date, objectName);
        this.setWeight(weight);
    }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }


    @Override
    public String toString() {
        return super.toString() + ", \"weight\" : " + weight + "}";
    }
    
}
