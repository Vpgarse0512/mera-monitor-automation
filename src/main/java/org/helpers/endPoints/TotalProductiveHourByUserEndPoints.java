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

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TotalProductiveHourByUserEndPoints extends RestUtils {

    private static final Logger LOGGER = LogManager.getLogger(TotalProductiveHourByUserEndPoints.class);
    public static final String PRODUCTIVE_HOURS_ENDPOINT = "GetProductiveHoursEndpoint";

    /**
     * Build request specification for get productive hours details.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForTotalProductiveHoursByUserAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token);
        LOGGER.info("Building request specification for total productive hours by user.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Total Productive Hours By User API with GET method and initialize the response object.
     */
    public void hitTotalProductiveHoursByUserAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(PRODUCTIVE_HOURS_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Total Productive Hours By User API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getTotalProductiveHoursByUserDetails() throws IOException, ParseException {
        LOGGER.info("Build request specification for Total Productive Hours By User API.");
        String accessToken = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> body = login.getTheDetailsFromLoginAPI();
        System.out.println(body);
        buildRequestSpecForTotalProductiveHoursByUserAPI(body, " "+accessToken);
        hitTotalProductiveHoursByUserAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        //Assert.assertEquals(getLoginAPIResponseMessage(), "success");
        return jsonPath;
    }
    public HashMap<String, String> getProductiveDetailsOfUser() throws IOException, ParseException {
        JsonPath productiveTime = getTotalProductiveHoursByUserDetails();
        HashMap<String, String> details=new HashMap<>();
        details.put("userName",productiveTime.getString("userName"));
        details.put("totalProductiveTime",productiveTime.getString("totalProductiveTime"));
        details.put("totalIdleTime",productiveTime.getString("totalIdleTime"));
        details.put("fromDate",productiveTime.getString("fromDate"));
        details.put("toDate",productiveTime.getString("toDate"));
        details.put("totalTime",productiveTime.getString("totalTime"));
        details.put("unproductiveTime",productiveTime.getString("unproductiveTime"));
        details.put("awayTime",productiveTime.getString("awayTime"));
        return details;
    }

    public static void main(String[] args) throws IOException, ParseException {
        TotalProductiveHourByUserEndPoints productive = new TotalProductiveHourByUserEndPoints();
        productive.getTotalProductiveHoursByUserDetails();
        System.out.println(jsonPath.get().toString());
        System.out.println(jsonPath.getString("[0].userName"));
        // to avoid 2 array entries use same custom date to get single entry and time should be morning to night
        System.out.println(productive.getProductiveDetailsOfUser());
    }

}
