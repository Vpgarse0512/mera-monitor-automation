package org.helpers.endPoints.userEndPointAPIs;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.helpers.enums.HttpVerbs;
import org.helpers.enums.Routes;
import org.helpers.jsonReader.JsonHelper;
import org.helpers.restUtil.RestUtils;
import org.json.simple.parser.ParseException;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.testng.Assert;
import org.timeUtil.TimeDateClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ScreenshotEndPoint extends RestUtils {


    private static final Logger LOGGER = LogManager.getLogger(NewTimeTrackerEndPoint.class);
    public static final String SCREENSHOTS_ENDPOINT = "GetScreenshotsEndPoint";

    /**
     * Build request specification for get Download time tracker report.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForScreenShotAPI(LinkedHashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Download time tracker report API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download time tracker report API with GET method and initialize the response object.
     */
    public void hitScreenShotAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(SCREENSHOTS_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Download time tracker report API with given request specification: " + reqSpec + "\n" +
                "and the endpoint is : " + endPoint + "\n" + "and with given http verb : " +
                HttpVerbs.GET.name());
        response = RestUtils.getResponse(
                reqSpec,
                HttpVerbs.GET.name(),
                endPoint
        );
        // Change the response to json path.
        jsonPath = RestUtils.rawToJson(response);
    }

    public int getAPIResponseCode() {
        return response.getStatusCode();
    }

    public String getLoginAPIResponseMessage() {
        return jsonPath.getString("message").trim();
    }

    public JsonPath getScreenShotDetails(String date,int day,String month,String email,String password) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("OrganizationId", data.get("organizationId"));
            object.put("UserId", data.get("userId"));
            object.put("Email", data.get("email"));
            object.put("ReportDate", date);
            buildRequestSpecForScreenShotAPI(object, accessToken);
            hitScreenShotAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public LinkedHashMap<String, ArrayList<String>> getScreenShotStamp(String date,int day,String month,String email,String password) {
        JsonPath data = getScreenShotDetails(date, day, month, email, password);
        int size = data.getList("blobResponse").size();
        System.out.println(size);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(data.get("blobResponse[" + i + "].description"));
        }

        LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap();
        map.put("timeStamp", list);

        return map;
    }

    public static void main(String[] args) throws IOException, ParseException {
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        ScreenshotEndPoint screenshot = new ScreenshotEndPoint();
        //System.out.println(screenshot.getScreenShotDetails(TimeDateClass.getTodaysDate()).getString("blobResponse[0].description"));
        System.out.println(screenshot.getScreenShotDetails(TimeDateClass.getCustomDate(16,"January"),2,month,email,password).getString(""));
       // System.out.println(screenshot.getScreenShotStamp(TimeDateClass.getTodaysDate(),16,month,email,password));
    }
}
