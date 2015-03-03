package webapi.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.entities.Data;
import webapi.service.DataServiceImpl;

import javax.websocket.server.PathParam;

@RestController("dataController")
public class DataController {

    // JSON files path
    private final String JSON_FILES_FOLDER = System.getProperty("user.dir") + "/src/main/resources/";
    private final String WEIGHT_JSON_PATH = JSON_FILES_FOLDER + "weight.json";
    private final String BODY_FAT_JSON_PATH = JSON_FILES_FOLDER + "body_fat.json";
    private final String HEART_RATE_JSON_PATH = JSON_FILES_FOLDER + "heart_rate.json";
    private final String OBJECTS_INFOS_JSON_PATH = JSON_FILES_FOLDER + "objects_infos.json";

    // Logger
    private Logger logger = Logger.getLogger(DataController.class.getName());


    /** ########## Getters archives JSON ########## **/

    @RequestMapping("/test")
    public String test() {
        return "Test OK! :D";
    }

    @RequestMapping("/weight/archives")
    public String getWeightJSON() {

        logger.info("New call to the weight WS");

        BufferedReader br = null;
        String weightJSON = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(WEIGHT_JSON_PATH));
            while ((sCurrentLine = br.readLine()) != null) {
                weightJSON += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return weightJSON;

    }

    @RequestMapping("/body_fat/archives")
    public String getBodyFatJSON() {

        logger.info("New call to the body fat WS");

        BufferedReader br = null;
        String bodyFatJSON = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(BODY_FAT_JSON_PATH));
            while ((sCurrentLine = br.readLine()) != null) {
                bodyFatJSON += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return bodyFatJSON;

    }

    @RequestMapping("/heart_rate/archives")
    public String getHeartRateJSON() {

        logger.info("New call to the heart rate WS");

        BufferedReader br = null;
        String heartRateJSON = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(HEART_RATE_JSON_PATH));
            while ((sCurrentLine = br.readLine()) != null) {
                heartRateJSON += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return heartRateJSON;

    }

    @RequestMapping("/objects_infos/archives")
    public String getObjectsInfosJSON() {

        logger.info("New call to the objects infos WS");

        BufferedReader br = null;
        String objectsInfosJSON = "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(OBJECTS_INFOS_JSON_PATH));
            while ((sCurrentLine = br.readLine()) != null) {
                objectsInfosJSON += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return objectsInfosJSON;

    }

    /** ########## Add/get datas ########## **/

    @RequestMapping("/db_data")
    public String getDBdata() {
        logger.info("New call to get database objets WS");

        String res = "";
        DataServiceImpl dsi = new DataServiceImpl();
        Iterable<Data> resList = dsi.getAllDataFromDatabase();
        if ( resList.iterator().hasNext() == false )
            return "Empty DB.";
        else {
            Iterator it = resList.iterator();
            while ( it.hasNext() ) {
                res += it.toString();
            }
        }
        return res;
    }

    @RequestMapping("/weight/{objname}/{weight}")
    public void addWeightData(@PathParam("objname") String objname,
                              @PathParam("weight") String weight) {
        logger.info("New call to add weight data WS");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd HH:mm:ss
        Date d = new Date();
        String date = dateFormat.format(d);
        DataServiceImpl dsi = new DataServiceImpl();
        try {
            dsi.addNewWeightData(date, objname, weight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
