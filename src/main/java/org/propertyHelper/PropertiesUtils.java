package org.propertyHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

import static org.path.FilePathConfig.GLOBAL_PROPERTIES;


public class PropertiesUtils {


        public static final Logger LOGGER = LogManager.getLogger(PropertiesUtils.class);
    public String readProperty(String propertyName) {
        try {
            FileReader fileReader = new FileReader("./config/config.properties");
            Properties properties = new Properties();
            try {
                properties.load(fileReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return properties.getProperty(propertyName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static Properties properties = new Properties();
    static boolean configFileCheck = false;
    static boolean globalFileCheck = false;
    static boolean textFileCheck=false;
    static boolean apiConfigReaderCheck=false;

    static FileReader configReader;
    static FileReader globalReader;
    static FileReader textReader;
    static FileReader apiConfigReader;


    public static String getProperty(PropertyFileEnum propertyFileEnum, String propertyName) {
        switch (propertyFileEnum) {
            case CREAD:
                try {
                    if (configFileCheck != true) {
                        configReader = new FileReader("src/test/resources/user.properties");
                        configFileCheck = true;
                    }
                    properties.load(configReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case GLOB:
                try {
                    if (globalFileCheck != true) {
                        globalReader = new FileReader(GLOBAL_PROPERTIES);
                        globalFileCheck = true;
                    }
                    properties.load(globalReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case TEXT:
                try {
                    if (textFileCheck != true) {
                        textReader = new FileReader("src/test/resources/text.properties");
                        textFileCheck = true;
                    }
                    properties.load(textReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case APICONFIG:
                try {
                    if (apiConfigReaderCheck != true) {
                        apiConfigReader = new FileReader("src/test/resources/apiConfig.properties");
                        apiConfigReaderCheck = true;
                    }
                    properties.load(apiConfigReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return properties.getProperty(propertyName);
    }
    public static void modifyProperty(PropertyFileEnum propertyFileEnum, String key, String newValue) {
        switch (propertyFileEnum) {
            case CREAD:
                try {
                    if (configFileCheck != true) {
                        configReader = new FileReader("src/test/resources/user.properties");
                        configFileCheck = true;
                    }
                    properties.load(configReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case GLOB:
                try {
                    if (globalFileCheck != true) {
                        globalReader = new FileReader(GLOBAL_PROPERTIES);
                        globalFileCheck = true;
                    }
                    properties.load(globalReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case TEXT:
                try {
                    if (textFileCheck != true) {
                        textReader = new FileReader("src/test/resources/text.properties");
                        textFileCheck = true;
                    }
                    properties.load(textReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case APICONFIG:
                try {
                    if (apiConfigReaderCheck != true) {
                        apiConfigReader = new FileReader("src/test/resources/apiConfig.properties");
                        apiConfigReaderCheck = true;
                    }
                    properties.load(apiConfigReader);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
         properties.setProperty(key, newValue);

        try (FileOutputStream fileOutputStream = new FileOutputStream(GLOBAL_PROPERTIES)) {
            // Save the modified properties back to the file
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
