package org.helpers.jsonReader;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonHelper {

    private static final Logger LOGGER = LogManager.getLogger(JsonHelper.class);

    public static Object getValue(String key) {

        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("src/test/resources/userCredentials.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject path = (JSONObject) obj;
        LOGGER.info("User data successfully printed !");
        return path.get(key);
    }


}
