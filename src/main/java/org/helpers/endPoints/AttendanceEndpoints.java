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

public class AttendanceEndpoints extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(AttendanceEndpoints.class);
    public static final String ATTENDANCE_ENDPOINT = "GetAttendanceEndPoint";

    /**
     * Build request specification for Get Attendance Report By Report Manager API.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForAttendanceAPI(LinkedHashMap<String, Object> payloadObj, Object token) {
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
        Routes routes = Routes.valueOf(ATTENDANCE_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Attendance Report By Report Manager API with given request specification: " + reqSpec + "\n" +
                "and the endpoint is : " + endPoint + "\n" + "and with given http verb : " +
                HttpVerbs.POST.name());
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

    public String getResponseMessage() {
        return jsonPath.getString("message").trim();
    }

    public JsonPath getAttendanceDetails(int day, String month) {
        LOGGER.info("Build request specification for Attendance Report By Report Manager API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("ManagerId", data.get("userId"));
            object.put("OrganizationId", data.get("organizationId"));
            object.put("FromDate", TimeDateClass.getCustomDate(day, month));
            object.put("ToDate", TimeDateClass.getCustomDate(day, month));
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
    public HashMap<String, Object> getUserAttendanceDetailsInMap(int day,String month)
    {
        HashMap<String,Object> map=new HashMap<>();
        String name=getAttendanceDetails(day,month).getString("userName").replaceAll("\\[","").replaceAll("\\]","");
        map.put("name",name);
        String expectedHour=getAttendanceDetails(day,month).getString("totalExpectedHours").replaceAll("\\[","").replaceAll("\\]","");
        map.put("expectedHour",expectedHour);
        String loggedHour=getAttendanceDetails(day,month).getString("activeTime").replaceAll("\\[","").replaceAll("\\]","");
        double loggedHours = Double.parseDouble(loggedHour);
        map.put("loggedHours",TimeDateClass.convertSecondsToHHMMSSFormat(loggedHours));
        String holiday=getAttendanceDetails(day,month).getString("holiday").replaceAll("\\[","").replaceAll("\\]","");
        map.put("holiday",holiday);
        String leaves=getAttendanceDetails(day,month).getString("leaves").replaceAll("\\[","").replaceAll("\\]","");
        map.put("leaves",leaves);
        return map;
    }


    public static void main(String[] args) {
        AttendanceEndpoints attendance=new AttendanceEndpoints();
        System.out.println(attendance.getAttendanceDetails(16,"January").getString(""));
        System.out.println(attendance.getUserAttendanceDetailsInMap(16,"January"));
    }

}
