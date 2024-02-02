package org.helpers.endPoints.userEndPointAPIs;

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
    public void buildRequestSpecForLoginAPI(HashMap<String,Object> payloadObj) {
        LOGGER.info("Building request specification for Login API.");
        reqSpec = RestUtils.requestSpecification(payloadObj);
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
        JSONObject object = new JSONObject();
        object.put("email", JsonHelper.getValue("email1"));
        object.put("password", JsonHelper.getValue("password1"));
        buildRequestSpecForLoginAPI(object);
        hitLoginAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        return jsonPath;
    }
    public LinkedHashMap<String, Object> getTheDetailsFromLoginAPI(int day,String month,Object email, Object pass) throws IOException, ParseException {
        LOGGER.info("Build request specification for Login API.");
        JSONObject object = new JSONObject();
        object.put("email", email);
        object.put("password",pass);
        buildRequestSpecForLoginAPI(object);
        hitLoginAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        PropertiesUtils.modifyProperty(PropertyFileEnum.GLOB,"accessToken",jsonPath.getString("accessToken"));
        LinkedHashMap<String,Object> body = new LinkedHashMap<>();
        body.put("accessToken",jsonPath.getString("accessToken"));
        body.put("message",jsonPath.getString("message"));
        body.put("email",jsonPath.getString("email"));
        body.put("userId", jsonPath.getString("userId"));
        body.put("organizationId", jsonPath.getString("organizationId"));
        body.put("fromDate", TimeDateClass.getCustomDateAndTime(day,month));
        body.put("toDate",TimeDateClass.getCustomDateAndTime(day,month));
        return body;
    }

    public static void main(String[] args) throws IOException, ParseException {
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        LoginEndPoints login=new LoginEndPoints();
        login.getLoginUserDetails();
        System.out.println(jsonPath.getString("accessToken"));
        Object inemail = JsonHelper.getValue("invalidEmail");
        Object pass = JsonHelper.getValue("invalidPassword");
        System.out.println(login.getTheDetailsFromLoginAPI(16,month,inemail,pass));

    }

}
