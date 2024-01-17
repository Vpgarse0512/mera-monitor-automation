package org.helpers.endPoints;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.helpers.enums.HttpVerbs;
import org.helpers.enums.Routes;
import org.helpers.jsonReader.JsonHelper;
import org.helpers.restUtil.RestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.testng.Assert;
import org.timeUtil.TimeDateClass;

import java.io.IOException;
import java.util.*;

public class LoginEndPoints extends RestUtils {

    private static final Logger LOGGER = LogManager.getLogger(LoginEndPoints.class);
    public static final String LOGIN_ENDPOINT = "GetLoginEndpoint";

    /**
     * Build request specification for get login details.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForLoginAPI(HashMap<String,Object> payloadObj, Object token) {
        HashMap<String,Object> accessToken=new HashMap();
        accessToken.put("accessToken",token);
        LOGGER.info("Building request specification for Login API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Product Details API with GET method and initialize the response object.
     */
    public void hitLoginAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(LOGIN_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Product Details API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getLoginUserDetails() throws IOException, ParseException {
        LOGGER.info("Build request specification for Login API.");
        String accessToken = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        JSONObject object = new JSONObject();
        object.put("email", JsonHelper.getValue("email1"));
        object.put("password", JsonHelper.getValue("password1"));
        buildRequestSpecForLoginAPI(object, accessToken);
        hitLoginAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        return jsonPath;
    }
    public LinkedHashMap<String, Object> getTheDetailsFromLoginAPI() throws IOException, ParseException {
        LOGGER.info("Build request specification for Login API.");
        String accessToken = PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        JSONObject object = new JSONObject();
        object.put("email", JsonHelper.getValue("email1"));
        object.put("password", JsonHelper.getValue("password1"));
        buildRequestSpecForLoginAPI(object, accessToken);
        hitLoginAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        LinkedHashMap<String,Object> body = new LinkedHashMap<>();
        body.put("userId", jsonPath.getString("userId"));
        body.put("organizationId", jsonPath.getString("organizationId"));
        body.put("fromDate", TimeDateClass.getCustomDateAndTime(8,"06:00:00"));
        body.put("toDate",TimeDateClass.getCustomDateAndTime(8,"20:00:00"));
        return body;
    }

    public static void main(String[] args) throws IOException, ParseException {
        LoginEndPoints login=new LoginEndPoints();
        login.getLoginUserDetails();
        System.out.println(jsonPath.getString("email"));
        System.out.println(login.getTheDetailsFromLoginAPI());

    }

}
