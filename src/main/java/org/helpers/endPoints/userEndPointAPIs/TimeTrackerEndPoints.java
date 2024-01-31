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

public class TimeTrackerEndPoints extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(TimeTrackerEndPoints.class);
    public static final String TIME_TRACKER_ENDPOINT = "GetUserTimeTrackerEndPoint";

    /**
     * Build request specification for get Download time tracker report.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForTimeTrackerAPI(HashMap<String,Object> payloadObj, Object token) {
        HashMap<String,Object> accessToken=new HashMap();
        accessToken.put("Authorization",token); // accessToken
        LOGGER.info("Building request specification for Download time tracker report API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download time tracker report API with GET method and initialize the response object.
     */
    public void hitTimeTrackerAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(TIME_TRACKER_ENDPOINT);
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

    public JsonPath getTimeTrackerDetails(int day)  {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer "+PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login=new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String,Object> object = new LinkedHashMap<>();
            object.put("fromDate",TimeDateClass.getCustomDateAndTime(day,"00:00:00"));
            object.put("toDate",TimeDateClass.getCustomDateAndTime(day,"00:00:00"));
            object.put("organizationId",data.get("organizationId"));
            object.put("userType",PropertiesUtils.getProperty(PropertyFileEnum.CREAD,"userType"));
            object.put("userId",data.get("userId"));
            System.out.println(object);
            System.out.println(accessToken);
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
    /*{
        "userId": "645caeccc80c1518c6e1dac1",
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
            "timeZone": "IST(UTC+05:30)"
    }*/
    public void getTimeTrackerDataFromAPI() throws IOException, ParseException {
        JsonPath details = getTimeTrackerDetails(3);
        HashMap map=new HashMap();
        map.put("employeeName",details.getString("[1].employeeName"));
        map.put("reportDate",TimeDateClass.convertDateFormat(details.getString("date"),"d/m/yyyy","dd-MM-yyyy"));
        map.put("inTime",details.getString(""));
        map.put("outTime",details.getString(""));
        map.put("attendance",details.getString(""));
        map.put("email",details.getString(""));
        map.put("role",details.getString(""));
        map.put("department",details.getString(""));
        map.put("reportHead",details.getString(""));
        map.put("activeTime",details.getString(""));
        map.put("productiveTime",details.getString(""));
        map.put("unproductiveTime",details.getString(""));
        map.put("idleTime",details.getString(""));
        map.put("totalTime",details.getString(""));
        map.put("awayTime",details.getString(""));
    }


    public static void main(String[] args)  {
        TimeTrackerEndPoints tracker=new TimeTrackerEndPoints();
        JsonPath path = tracker.getTimeTrackerDetails(3);
        System.out.println(path.getString(""));
    }


}