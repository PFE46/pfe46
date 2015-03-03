package webapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import webapi.entities.BodyFatData;
import webapi.entities.Data;
import webapi.entities.HeartRateData;
import webapi.entities.WeightData;
import webapi.repository.DataRepository;

import java.io.*;

@Service
@Component
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public Iterable<Data> getAllDataFromDatabase() {
        System.out.println("\tCall to getAllDataFromDatabase()");
        System.out.println("\t\twith this.dataRepository = " + this.dataRepository.toString());
        return this.dataRepository.findAll();
    }

    @Override
    public void addNewWeightData(String date, String objectName, String weight) throws FileNotFoundException, IOException {
        WeightData wd = new WeightData(date, objectName, weight);
        this.dataRepository.save(wd);
    }

    @Override
    public void addNewHeartRateData(String date, String objectName, String heartRate) throws FileNotFoundException, IOException {
        HeartRateData hrd = new HeartRateData(date, objectName, heartRate);
        this.dataRepository.save(hrd);
    }

    @Override
    public void addNewBodyFatData(String date, String objectName, String bodyFat) throws FileNotFoundException, IOException {
        BodyFatData bfd = new BodyFatData(date, objectName, bodyFat);
        this.dataRepository.save(bfd);
    }

    @Override
    public void modifyObjectInfosData(String objectName, String status, String batteryLevel) throws FileNotFoundException, IOException {

    }

    @Override
    public void modifyObjectInfosData(long id, String status, String batteryLevel) throws FileNotFoundException, IOException {

    }
}
