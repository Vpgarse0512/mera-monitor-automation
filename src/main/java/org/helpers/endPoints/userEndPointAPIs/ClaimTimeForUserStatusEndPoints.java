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

public class ClaimTimeForUserStatusEndPoints extends RestUtils{
    private static final Logger LOGGER = LogManager.getLogger(ClaimTimeForUserStatusEndPoints.class);
    public static final String CLAIM_TIME_ENDPOINT = "GetClaimTimeForUser";

    /**
     * Build request specification for get Claim Time For User.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForClaimTimeForUserAPI(HashMap<String,Object> payloadObj, Object token) {
        HashMap<String,Object> accessToken=new HashMap();
        accessToken.put("Authorization",token); // accessToken
        LOGGER.info("Building request specification for ClaimTimeForUser API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Download time tracker report API with GET method and initialize the response object.
     */
    public void hitClaimTimeForUserAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(CLAIM_TIME_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the ClaimTimeForUser API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getTimeTrackerDetails(int day,String month,String email,String password)  {
        LOGGER.info("Build request specification for Download time tracker report API.");
        String accessToken = "Bearer "+ PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login=new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String,Object> object = new LinkedHashMap<>();
            object.put("fromDate", TimeDateClass.getCustomDate(day,month));
            object.put("toDate",TimeDateClass.getCustomDate(day,month));
            object.put("organizationId",data.get("organizationId"));
            object.put("userType",PropertiesUtils.getProperty(PropertyFileEnum.CREAD,"userType"));
            object.put("userId",data.get("userId"));
            buildRequestSpecForClaimTimeForUserAPI(object, accessToken);
            hitClaimTimeForUserAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }
    public LinkedHashMap getClaimData(int day,String month,String email,String password)
    {
        JsonPath claim = getTimeTrackerDetails(day,month,email,password);
        LinkedHashMap map=new LinkedHashMap();
        map.put("timeClaimId",claim.getString("timeClaimId").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("userName",claim.getString("userName").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("claimStatus",claim.getString("claimStatus").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("toTime",claim.getString("toTime").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("fromTime",claim.getString("fromTime").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("reason",claim.getString("reason").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("responseBy",claim.getString("responseBy").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("activityStatus",claim.getString("activityStatus").replaceAll("\\[","").replaceAll("\\]",""));
        map.put("recordDate",claim.getString("recordDate").replaceAll("\\[","").replaceAll("\\]",""));
        return map;
    }



    public static void main(String[] args) throws IOException, ParseException {
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        ClaimTimeForUserStatusEndPoints claim=new ClaimTimeForUserStatusEndPoints();
        //System.out.println(StringUtil.extractTime(claim.getClaimData(3).get("toTime")));
        //System.out.println(claim.getTimeTrackerDetails(3,"january").getString(""));
        System.out.println(claim.getClaimData(16,month,email,password));
    }
}
