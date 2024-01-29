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

public class ProductivityVsIdleEndPoints extends RestUtils {

    private static final Logger LOGGER = LogManager.getLogger(ProductivityVsIdleEndPoints.class);
    public static final String SYSTEM_ACTIVITY_ENDPOINT = "GetTotalProductiveHoursByUserEndPoint";

    /**
     * Build request specification for get Productivity Vs Idle.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForProductivityVsIdleAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Productivity Vs Idle API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Productivity Vs Idle API with GET method and initialize the response object.
     */
    public void hitProductivityVsIdleAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(SYSTEM_ACTIVITY_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Productivity Vs Idle API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getTotalProductiveHoursByUserDetails(int day,String month) {
        LOGGER.info("Build request specification for Productivity Vs Idle API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("userId", data.get("userId"));
            object.put("organizationId", data.get("organizationId"));
            object.put("fromDate", TimeDateClass.getCustomDate(day, month));
            object.put("toDate", TimeDateClass.getCustomDate(day, month));
            buildRequestSpecForProductivityVsIdleAPI(object, accessToken);
            hitProductivityVsIdleAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }
    public HashMap<String, Object> getProductivityVsIdleDetailsMap(int day,String month) {
        HashMap<String, Object> object = new HashMap<>();
        JsonPath productivity = new ProductivityVsIdleEndPoints().getTotalProductiveHoursByUserDetails(day,month);
        object.put("name",productivity.getString("userName").toString().replaceAll("\\[","").replaceAll("\\]",""));
        String productiveTime=TimeDateClass.convertSecondsToHHMMSSFormat(productivity.getString("totalProductiveTime").toString().replaceAll("\\[","").replaceAll("\\]",""));
        object.put("productiveTime",productiveTime);
        String unproductiveTime=TimeDateClass.convertSecondsToHHMMSSFormat(productivity.getString("unproductiveTime").toString().replaceAll("\\[","").replaceAll("\\]",""));
        object.put("unproductiveTime",unproductiveTime);
        String totalIdleTime=TimeDateClass.convertSecondsToHHMMSSFormat(productivity.getString("totalIdleTime").toString().replaceAll("\\[","").replaceAll("\\]",""));
        object.put("idleTime",totalIdleTime);
        String awayTime=TimeDateClass.convertSecondsToHHMMSSFormat(productivity.getString("awayTime").toString().replaceAll("\\[","").replaceAll("\\]",""));
        object.put("awayTime",awayTime);
        String totalTime=TimeDateClass.convertSecondsToHHMMSSFormat(productivity.getString("totalTime").toString().replaceAll("\\[","").replaceAll("\\]",""));
        object.put("totalTime",totalTime);
        return object;
    }
    public static void main(String[] args) {
        ProductivityVsIdleEndPoints productivity = new ProductivityVsIdleEndPoints();
        System.out.println(productivity.getProductivityVsIdleDetailsMap(18,"January"));
    }
}
