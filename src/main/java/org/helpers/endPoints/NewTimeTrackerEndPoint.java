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
import java.util.List;
import java.util.Map;

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

    public JsonPath getTimeTrackerRangData(int fromDay, int toDay) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("fromDate", TimeDateClass.getCustomDateAndTime(fromDay, "00:00:00"));
            object.put("toDate", TimeDateClass.getCustomDateAndTime(toDay, "00:00:00"));
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
        String totalTime = tracker.getString("totalTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("totalTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(totalTime)));
        String activeTime = tracker.getString("totalActiveTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("activeTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(activeTime)));
        String idleTime = tracker.getString("totalIdleTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("idleTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(idleTime)));
        String awayTime = tracker.getString("awayTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("awayTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(awayTime)));
        map.put("date", tracker.getString("date").replaceAll("\\[", "").replaceAll("\\]", ""));
        String firstActivity = tracker.getString("firstActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("firstActivity", TimeDateClass.convertDateFormat(firstActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        String lastActivity = tracker.getString("lastActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("lastActivity", TimeDateClass.convertDateFormat(lastActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        map.put("dept", tracker.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
        return map;
    }

    public Map<String, List<String>> range(int size, String[] strList, int toDay, String date) {
        Map<String, List<String>> allRowsData = new HashMap<>();

        for (String key : strList) {
            List<String> cellData = new java.util.ArrayList<>();
            for (int i = 0; i <= size - 1; i++) {
                //soft.assertEquals(time.);
                String data = getTimeTrackerRangData(toDay, Integer.parseInt(date)).getString("[" + i + "]." + key + "");
                cellData.add(data);
            }
            allRowsData.put(key, cellData);
        }
        return allRowsData;
    }

    public LinkedHashMap<String, Object> getRangeOfTimeTrackerData(int fromDay, int toDay) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        JsonPath tracker = getTimeTrackerRangData(fromDay, toDay);
        map.put("name", tracker.getString("userName").replaceAll("\\[", "").replaceAll("\\]", ""));
        String totalTime = tracker.getString("totalTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("totalTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(totalTime)));
        String activeTime = tracker.getString("totalActiveTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("activeTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(activeTime)));
        String idleTime = tracker.getString("totalIdleTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("idleTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(idleTime)));
        String awayTime = tracker.getString("awayTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("awayTime", TimeDateClass.convertSecondsToHHMMSSFormat(Double.parseDouble(awayTime)));
        map.put("date", tracker.getString("date").replaceAll("\\[", "").replaceAll("\\]", ""));
        String firstActivity = tracker.getString("firstActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("firstActivity", TimeDateClass.convertDateFormat(firstActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        String lastActivity = tracker.getString("lastActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("lastActivity", TimeDateClass.convertDateFormat(lastActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        map.put("dept", tracker.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
        return map;
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
        System.out.println(tracker.getTimeTrackerDetails(17).getString(""));
        System.out.println(tracker.getTimeTrackerMapData(17));
        System.out.println(tracker.getTimeTrackerRangData(16, 18).getList("").size());
        System.out.println(tracker.getTimeTrackerRangData(16, 18).getString(""));
        String [] keys= {"userName","date","totalTime","totalActiveTime","totalIdleTime","awayTime","firstActivity","lastActivity","department","timeZone"};

        System.out.println(tracker.range(3,keys,16,"17"));;
    }
}
