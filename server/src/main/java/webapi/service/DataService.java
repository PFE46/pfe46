package webapi.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DataService {

    // ---------- Add new data functions ----------

    /**
     * Add a new weight data
     * @param objectName
     * @param date
     * @param weight
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addNewWeightData(String date, String objectName, String weight)
            throws FileNotFoundException, IOException;

    /**
     * Add a new heart rate data
     * @param date
     * @param heartRate
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addNewHeartRateData(String date, String objectName, String heartRate)
            throws FileNotFoundException, IOException;

    /**
     * Add a new body fat data
     * @param objectName
     * @param date
     * @param bodyFat
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addNewBodyFatData(String date, String objectName, String bodyFat)
            throws FileNotFoundException, IOException;



    // ---------- Modify object informations with object id or name ----------

    /**
     * Modify object information
     * @param objectName
     * @param status
     * @param batteryLevel
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void modifyObjectInfosData(String objectName, String status, String batteryLevel)
            throws FileNotFoundException, IOException;

    /**
     * Modify object information
     * @param id
     * @param status
     * @param batteryLevel
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void modifyObjectInfosData(long id, String status, String batteryLevel)
            throws FileNotFoundException, IOException;

}
