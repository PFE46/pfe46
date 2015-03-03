package webapi.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import webapi.entities.Data;
import webapi.service.DataService;

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

    @Autowired
    private DataService dataService;
    
    /** ########## Getters archives JSON ########## **/

    @RequestMapping(value = "/test",
            method = RequestMethod.GET,
            produces = "application/json")
    public String test() {
        return "Test OK! :D";
    }

    @RequestMapping(value = "/weight/archives",
            method = RequestMethod.GET,
            produces = "application/json")
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

    @RequestMapping(value = "/body_fat/archives",
            method = RequestMethod.GET,
            produces = "application/json")
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

    @RequestMapping(value = "/heart_rate/archives",
            method = RequestMethod.GET,
            produces = "application/json")
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

    @RequestMapping(value = "/objects_infos/archives",
            method = RequestMethod.GET,
            produces = "application/json")
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

    @RequestMapping(value = "/db_data",
            method = RequestMethod.GET,
            produces = "application/json")
    public String getDBdata() {
        logger.info("New call to get database objets WS");

        String res = "";
        Iterable<Data> resList = dataService.getAllDataFromDatabase();
        if ( resList.iterator().hasNext() == false )
            return "Empty DB.";
        else {
            Iterator it = resList.iterator();
            while ( it.hasNext() ) {
                res += it.toString();
            }
            return res;
        }
    }

    @RequestMapping(value = "/db_data/wii_bb",
            method = RequestMethod.GET,
            produces = "application/json")
    public String getWiiBBdata() {
        logger.info("New call to get Wii Balance Board data WS");

        String res = "";
        Iterable<Data> resList = dataService.getWiiBBDataFromDatabase();
        if ( resList.iterator().hasNext() == false )
            return "Empty DB.";
        else {
            return resList.iterator().next().toString();
            /*
            Iterator it = resList.iterator();
            while ( it.hasNext() ) {
                res += it.toString();
            }
            */
        }
    }

    @RequestMapping(value = "/weight/{objname}/{weight}") // method = RequestMethod.POST)
    public void addWeightData(@PathVariable String objname,
                              @PathVariable String weight) {
        logger.info("New call to add weight data WS");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd HH:mm:ss
        Date d = new Date();
        String date = dateFormat.format(d);
        try {
            dataService.addNewWeightData(date, objname, weight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
