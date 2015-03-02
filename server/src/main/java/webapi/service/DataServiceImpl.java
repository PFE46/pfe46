package webapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapi.entities.BodyFatData;
import webapi.entities.HeartRateData;
import webapi.entities.WeightData;
import webapi.repository.DataRepository;

import java.io.*;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;



    @Override
    public void addNewWeightData(String date, String objectName, String weight) throws FileNotFoundException, IOException {
        WeightData wd = new WeightData(date, objectName, weight);
        dataRepository.save(wd);
    }

    @Override
    public void addNewHeartRateData(String date, String objectName, String heartRate) throws FileNotFoundException, IOException {
        HeartRateData hrd = new HeartRateData(date, objectName, heartRate);
        dataRepository.save(hrd);
    }

    @Override
    public void addNewBodyFatData(String date, String objectName, String bodyFat) throws FileNotFoundException, IOException {
        BodyFatData bfd = new BodyFatData(date, objectName, bodyFat);
        dataRepository.save(bfd);
    }

    @Override
    public void modifyObjectInfosData(String objectName, String status, String batteryLevel) throws FileNotFoundException, IOException {

    }

    @Override
    public void modifyObjectInfosData(long id, String status, String batteryLevel) throws FileNotFoundException, IOException {

    }
}
