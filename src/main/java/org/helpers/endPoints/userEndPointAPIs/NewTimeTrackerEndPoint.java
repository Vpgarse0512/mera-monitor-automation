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

    public JsonPath getTimeTrackerDetails(int day,String month,String email,String password) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("fromDate", TimeDateClass.getCustomDateAndTime(day, month));
            object.put("toDate", TimeDateClass.getCustomDateAndTime(day, month));
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

    public JsonPath getTimeTrackerRangData(int today, int pastDay,int day,String month,String email,String password) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("fromDate", TimeDateClass.getCustomDateAndTime(today, month));
            object.put("toDate", TimeDateClass.getCustomDateAndTime(pastDay, month));
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

    public LinkedHashMap<String, Object> getTimeTrackerMapData(int day,String month,String email,String password) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        JsonPath tracker = getTimeTrackerDetails(day,month,email,password);
        System.out.println(tracker.getString(""));
        map.put("name", tracker.getString("userName").replaceAll("\\[", "").replaceAll("\\]", ""));
        String totalTime = tracker.getString("totalTime").replaceAll("\\[", "").replaceAll("\\]", "");
        System.out.println(totalTime);
        map.put("totalTime", TimeDateClass.convertSecondsToHHMMSSFormat(totalTime));
        String activeTime = tracker.getString("totalActiveTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("activeTime", TimeDateClass.convertSecondsToHHMMSSFormat(activeTime));
        String idleTime = tracker.getString("totalIdleTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("idleTime", TimeDateClass.convertSecondsToHHMMSSFormat(idleTime));
        String awayTime = tracker.getString("awayTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("awayTime", TimeDateClass.convertSecondsToHHMMSSFormat(awayTime));
        map.put("date", tracker.getString("date").replaceAll("\\[", "").replaceAll("\\]", ""));
        String firstActivity = tracker.getString("firstActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("firstActivity", TimeDateClass.convertDateFormat(firstActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        String lastActivity = tracker.getString("lastActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("lastActivity", TimeDateClass.convertDateFormat(lastActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        map.put("dept", tracker.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
        return map;
    }

    public Map<String, List<String>> range(int size, String[] strList, int toDay, String date,int day,String month,String email,String password) {
        Map<String, List<String>> allRowsData = new HashMap<>();
        JsonPath data = getTimeTrackerRangData(toDay, Integer.parseInt(date), day, month, email, password);
        for (String key : strList) {
            List<String> cellData = new java.util.ArrayList<>();
            for (int i = 0; i <= size - 1; i++) {
                //soft.assertEquals(time.);
                cellData.add(data.getString("[" + i + "]." + key + ""));
            }
            allRowsData.put(key, cellData);
        }
        return allRowsData;
    }

    public LinkedHashMap<String, Object> getRangeOfTimeTrackerData(int fromDay, int toDay,int day,String month,String email,String password) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        JsonPath tracker = getTimeTrackerRangData(fromDay, toDay,day,month,email,password);
        map.put("name", tracker.getString("userName").replaceAll("\\[", "").replaceAll("\\]", ""));
        String totalTime = tracker.getString("totalTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("totalTime", TimeDateClass.convertSecondsToHHMMSSFormat(totalTime));
        String activeTime = tracker.getString("totalActiveTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("activeTime", TimeDateClass.convertSecondsToHHMMSSFormat(activeTime));
        String idleTime = tracker.getString("totalIdleTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("idleTime", TimeDateClass.convertSecondsToHHMMSSFormat(idleTime));
        String awayTime = tracker.getString("awayTime").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("awayTime", TimeDateClass.convertSecondsToHHMMSSFormat(awayTime));
        map.put("date", tracker.getString("date").replaceAll("\\[", "").replaceAll("\\]", ""));
        String firstActivity = tracker.getString("firstActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("firstActivity", TimeDateClass.convertDateFormat(firstActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        String lastActivity = tracker.getString("lastActivity").replaceAll("\\[", "").replaceAll("\\]", "");
        map.put("lastActivity", TimeDateClass.convertDateFormat(lastActivity, "M/d/yyyy h:mm:ss a", "hh:mm:ss a"));
        map.put("dept", tracker.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
        return map;
    }

    public static void main(String[] args) throws IOException, ParseException {
        NewTimeTrackerEndPoint tracker = new NewTimeTrackerEndPoint();
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        int day = Integer.parseInt(JsonHelper.getValue("day").toString());
        //System.out.println(tracker.getTimeTrackerDetails(17).getString(""));
        System.out.println(tracker.getTimeTrackerMapData(02,"February","amkumar@aapnainfotech.com","Test@123"));
        //System.out.println(tracker.getTimeTrackerRangData(16, 18).getList("").size());
       // System.out.println(tracker.getTimeTrackerRangData(16, 18).getString(""));
        String [] keys= {"userName","date","totalTime","totalActiveTime","totalIdleTime","awayTime","firstActivity","lastActivity","department","timeZone"};
        //System.out.println(tracker.range(3,keys,16,"25",day,month,email,password));;
    }
}
