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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttendaceByUserEndPoint extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(AttendaceByUserEndPoint.class.getName());
    public static final String ATTENDANCE_BY_USER_ENDPOINT = "GetAttendaceByUserEndPoint";

    /**
     * Build request specification for Get Attendance Report By Report Manager API.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForAttendanceAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Attendance API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Attendance Report By Report Manager API with GET method and initialize the response object.
     */
    public void hitAttendanceAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(ATTENDANCE_BY_USER_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Attendance Report By Report Manager API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getAttendanceDetails(int day) {
        LOGGER.info("Build request specification for Attendance Report By Report Manager API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("userId", data.get("userId"));
            object.put("fromDate", TimeDateClass.getCustomDateAndTime(day, "00:00:00"));
            object.put("toDate", TimeDateClass.getCustomDateAndTime(day, "00:00:00"));
            object.put("organizationId", data.get("organizationId"));
            buildRequestSpecForAttendanceAPI(object, accessToken);
            hitAttendanceAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public Map<String, Object> getInOutTotalTime(int day) {
        Map<String, Object> map = new LinkedHashMap();
        JsonPath aatendance = new AttendaceByUserEndPoint().getAttendanceDetails(day);
        map.put("In", aatendance.getString("[0].title"));
        map.put("Out", aatendance.getString("[1].title"));
        map.put("Total", aatendance.getString("[2].title"));
        map.put("Presenty", aatendance.getString("[3].title"));
        return map;
    }

    public static void main(String[] args) {
        AttendaceByUserEndPoint aatendance = new AttendaceByUserEndPoint();
        System.out.println(aatendance.getAttendanceDetails(16).getString(""));
        System.out.println(aatendance.getInOutTotalTime(16));
    }
}
