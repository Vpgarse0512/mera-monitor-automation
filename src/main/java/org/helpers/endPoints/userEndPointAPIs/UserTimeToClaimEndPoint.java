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

public class UserTimeToClaimEndPoint extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(UserTimeToClaimEndPoint.class);
    public static final String TO_CLAIM_ENDPOINT = "GetUserTimeToClaim";

    /**
     * Build request specification for Get User Time To Claim.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForGetUserTimeToClaimAPI(LinkedHashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for ClaimTimeForUser API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Get User Time To Claim API with GET method and initialize the response object.
     */
    public void hitGetUserTimeToClaimAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(TO_CLAIM_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the GetUserTimeToClaim API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getUserTimeToClaimDetails(int day,String month,String email,String password) {
        LOGGER.info("Build request specification for Get User Time To Claim API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("UserId", data.get("userId"));
            object.put("OrganizationId", data.get("organizationId"));
            object.put("FromDate", TimeDateClass.getCustomDateAndTime(day, month));
            buildRequestSpecForGetUserTimeToClaimAPI(object, accessToken);
            hitGetUserTimeToClaimAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public HashMap<String, Object> getTimeClaimMap(int day,String month,String email,String password) {
        JsonPath claim = getUserTimeToClaimDetails(day,month,email,password);
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", TimeDateClass.getCustomDate(day,month).replaceAll("-", "/").split("T")[0]);
        map.put("firstActivity", claim.getString("firstActivity"));
        map.put("lastActivity", claim.getString("lastActivity"));
        map.put("totalTime", claim.getString("totalTime"));
        map.put("productiveTime", claim.getString("totalProductiveTime"));
        map.put("idleTime", claim.getString("totalIdleTime"));
        return map;
    }

    public static void main(String[] args) throws IOException, ParseException {
        UserTimeToClaimEndPoint claim = new UserTimeToClaimEndPoint();
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        //System.out.println(email +" "+password);
        System.out.println(claim.getUserTimeToClaimDetails(16,month,email,password).getString(""));
        System.out.println(claim.getTimeClaimMap(16,month,email,password));
    }
}