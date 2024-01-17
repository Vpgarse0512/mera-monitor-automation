package org.helpers.endPoints;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.helpers.enums.HttpVerbs;
import org.helpers.enums.Routes;
import org.helpers.jsonReader.JsonHelper;
import org.helpers.restUtil.RestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.testng.Assert;
import org.timeUtil.TimeDateClass;

import javax.sql.rowset.serial.SerialArray;
import java.io.IOException;
import java.sql.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemActivityEndPoints extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(SystemActivityEndPoints.class);
    public static final String SYSTEM_ACTIVITY_ENDPOINT = "GetSystemActivityEndPoint";

    /**
     * Build request specification for get system activity.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForSystemActivityAPI(HashMap<String,Object> payloadObj, Object token) {
        HashMap<String,Object> accessToken=new HashMap();
        accessToken.put("Authorization",token); // accessToken
        LOGGER.info("Building request specification for Download time tracker report API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download system activity API with GET method and initialize the response object.
     */
    public void hitSystemActivityAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(SYSTEM_ACTIVITY_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the system activity API with given request specification: " + reqSpec + "\n" +
                "and the endpoint is : " + endPoint + "\n" + "and with given http verb : " +
                HttpVerbs.POST.name());
        response = RestUtils.getResponse(
                reqSpec,
                HttpVerbs.POST.name(),
                endPoint
        );
        // Change the response to json path.
        jsonPath = RestUtils.rawToJson(response);
    }

    public int getAPIResponseCode() {
        return response.getStatusCode();
    }

    public String getResponseMessage() {
        return jsonPath.getString("message").trim();
    }
    public JsonPath getSystemActivityDetails()  {
        LOGGER.info("Build request specification for system activity API.");
        String accessToken = "Bearer "+PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login=new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String,Object> object = new LinkedHashMap<>();
            object.put("userId",data.get("userId"));
            object.put("date", TimeDateClass.getCustomDateAndTime(03,"00:00:00"));
            buildRequestSpecForSystemActivityAPI(object, accessToken);
            hitSystemActivityAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public static void main(String[] args) {
        SystemActivityEndPoints endpoint=new SystemActivityEndPoints();
        //System.out.println(endpoint.getSystemActivityDetails().getString("titleName"));
        /*JSONObject titleNames = endpoint.getSystemActivityDetails();
        Iterator<JSONObject>iterator=titleNames.values().iterator();

        while (iterator.hasNext())
        {
            JSONObject childObject = iterator.next();
           String title= (String) childObject.get("titleName");
            System.out.println(title);
        }*/
        for (int i=0;i<10;i++)
        {
            System.out.println(endpoint.getSystemActivityDetails().getString("["+i+"].titleName"));
        }
    }
}
