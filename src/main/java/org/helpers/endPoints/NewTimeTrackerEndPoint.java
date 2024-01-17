package org.helpers.endPoints;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.helpers.enums.HttpVerbs;
import org.helpers.enums.Routes;
import org.helpers.restUtil.RestUtils;
import org.json.simple.parser.ParseException;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.testng.Assert;
import org.timeUtil.TimeDateClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NewTimeTrackerEndPoint extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(NewTimeTrackerEndPoint.class);
    public static final String NEW_TIME_TRACKER_ENDPOINT = "GetNewTimeTrackerEndPoint";

    /**
     * Build request specification for get Download time tracker report.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForTimeTrackerAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Download time tracker report API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download time tracker report API with GET method and initialize the response object.
     */
    public void hitTimeTrackerAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(NEW_TIME_TRACKER_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Download time tracker report API with given request specification: " + reqSpec + "\n" +
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

    public String getLoginAPIResponseMessage() {
        return jsonPath.getString("message").trim();
    }

    public JsonPath getTimeTrackerDetails(int day) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("fromDate", TimeDateClass.getCustomDateAndTime(day, "00:00:00"));
            object.put("toDate", TimeDateClass.getCustomDateAndTime(day, "00:00:00"));
            object.put("organizationId", data.get("organizationId"));
            object.put("userId", data.get("userId"));
            buildRequestSpecForTimeTrackerAPI(object, accessToken);
            hitTimeTrackerAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public LinkedHashMap<String, Object> getTimeTrackerMapData(int day) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        JsonPath tracker = getTimeTrackerDetails(day);
        map.put("name", tracker.getString("userName").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("totalTime", tracker.getString("totalTime").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("activeTime", tracker.getString("totalActiveTime").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("idleTime", tracker.getString("totalIdleTime").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("awayTime", tracker.getString("awayTime").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("date", tracker.getString("date").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("firstActivity", tracker.getString("firstActivity").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("lastActivity", tracker.getString("lastActivity").replaceAll("\\[", "").replaceAll("\\]", ""));
        map.put("dept", tracker.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
        return map;
    }

    public void getRangeOfTimeTrackerData() {

    }
           /* "userId": "645caeccc80c1518c6e1dac1",
                    "userName": "Aman Kumar",
                    "totalTime": "10650.545",
                    "totalActiveTime": "10284.545",
                    "totalIdleTime": "0",
                    "awayTime": "366",
                    "awayTimePercent": "3.44",
                    "totalActivePercent": "96.56",
                    "totalIdlePercent": "0.00",
                    "message": "",
                    "date": "1/3/2024 12:00:00 AM",
                    "firstActivity": "1/3/2024 4:07:00 PM",
                    "lastActivity": "1/3/2024 7:04:31 PM",
                    "department": "Human Resource",
                    "timeZone": "IST(UTC+05:30)"*/

    public static void main(String[] args) {
        NewTimeTrackerEndPoint tracker = new NewTimeTrackerEndPoint();
        //System.out.println(tracker.getTimeTrackerDetails(3).getString(""));
        System.out.println(tracker.getTimeTrackerMapData(3));
    }
}
