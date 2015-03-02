package webapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    // JSON files path
    private final String JSON_FILES_FOLDER = System.getProperty("user.dir") + "/src/main/resources/";
    private final String WEIGHT_JSON_PATH = JSON_FILES_FOLDER + "weight.json";
    private final String BODY_FAT_JSON_PATH = JSON_FILES_FOLDER + "body_fat.json";
    private final String HEART_RATE_JSON_PATH = JSON_FILES_FOLDER + "heart_rate.json";
    private final String OBJECTS_INFOS_JSON_PATH = JSON_FILES_FOLDER + "objects_infos.json";

    // Logger
    private Logger logger = Logger.getLogger(DataController.class.getName());



    @RequestMapping("/test")
    public String test() {
        return "Test OK! :D";
    }

    @RequestMapping("/weight")
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

    @RequestMapping("/body_fat")
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

    @RequestMapping("/heart_rate")
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

    @RequestMapping("/objects_infos")
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

}
