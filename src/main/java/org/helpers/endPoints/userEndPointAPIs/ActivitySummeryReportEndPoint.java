package org.helpers.endPoints.userEndPointAPIs;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActivitySummeryReportEndPoint extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(ActivitySummeryReportEndPoint.class);
    public static final String ACTIVITY_SUMMERY_ENDPOINT = "GetSystemActivityReport";

    /**
     * Build request specification for get activity summery time tracker report.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForActivitySummeryAPI(LinkedHashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Download time tracker report API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download time tracker report API with GET method and initialize the response object.
     */
    public void hitActivitySummeryAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(ACTIVITY_SUMMERY_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Download activity summery API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getActivitySummeryDetails(int day, String month) {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("OrganizationId", data.get("organizationId"));
            object.put("UserId", data.get("userId"));
            object.put("ReportDate", TimeDateClass.getCustomDateAndTime(day, "00:00:00'Z'"));
            //System.out.println(object);
            //System.out.println(accessToken);
            buildRequestSpecForActivitySummeryAPI(object, accessToken);
            hitActivitySummeryAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public HashMap<String, Object> getActivitySummeryResponse(int day, String month) {
        JsonPath activity = new ActivitySummeryReportEndPoint().getActivitySummeryDetails(day, month);
        HashMap<String, Object> map = new HashMap();
        map.put("firstActivity", TimeDateClass.convertTimeFormat(activity.getString("firstActivity"), "M/dd/yyyy hh:mm:ss a", "hh:mm:ss a"));
        map.put("lastActivity", TimeDateClass.convertTimeFormat(activity.getString("lastActivity"), "M/dd/yyyy hh:mm:ss a", "hh:mm:ss a"));
        map.put("totalTime", TimeDateClass.convertSecondsToHHMMSSFormat(activity.getString("totalTime")));
        map.put("totalProductiveTime", TimeDateClass.convertSecondsToHHMMSSFormat(activity.getString("totalProductiveTime")));
        map.put("totalIdleTime", TimeDateClass.convertSecondsToHHMMSSFormat(activity.getString("totalIdleTime")));
        return map;
    }

    public LinkedHashMap<String, ArrayList<String>> getTableActivitySummeryResponse(int day, String month) {
        JsonPath activity = new ActivitySummeryReportEndPoint().getActivitySummeryDetails(day, month);
        int size = activity.getList("details").size();
        System.out.println(size);
        LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<>();
        String list[] = {"startTime", "endTime", "spentTime", "userActivityStatus"};
        for (String key : list) {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 0; i <= size - 1; i++) {

                array.add(activity.getString("details[" + i + "]." + key + ""));
            }
            map.put(key,array);
        }
       return map;
    }

    public static void main(String[] args) {
        ActivitySummeryReportEndPoint activity = new ActivitySummeryReportEndPoint();
        //System.out.println(activity.getActivitySummeryDetails(30, "January").getString(""));
        //System.out.println(activity.getActivitySummeryResponse(30, "January").get("totalIdleTime"));
        activity.getTableActivitySummeryResponse(30, "January");
        // System.out.println(activity.getTableActivitySummeryResponse(30, "January"));
    }
}
