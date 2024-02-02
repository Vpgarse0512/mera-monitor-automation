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

public class WebAndAppsEndPoints extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(WebAndAppsEndPoints.class);
    public static final String WEB_AND_APPS_ENDPOINT = "GetWebAndAppsEndPoints";

    /**
     * Build request specification for get Web and Apps API.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForWebAndAppsAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for Web and Apps API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Web and Apps API with GET method and initialize the response object.
     */
    public void hitWebAndAppsAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(WEB_AND_APPS_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the Web and Apps API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getGetWebAndAppsDetails() {
        LOGGER.info("Build request specification for Web and Apps API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        //data = login.getTheDetailsFromLoginAPI((String) JsonHelper.getValue("email1"), (String) JsonHelper.getValue("password1"));
        LinkedHashMap<String, Object> object = new LinkedHashMap<>();
        object.put("userId", data.get("userId"));
        object.put("organizationId", data.get("organizationId"));
        object.put("fromDate", TimeDateClass.getCustomDateAndTime(03, "January"));
        object.put("toDate", TimeDateClass.getCustomDateAndTime(03, "January"));
        buildRequestSpecForWebAndAppsAPI(object, accessToken);
        hitWebAndAppsAPI();
        Assert.assertEquals(getAPIResponseCode(), 200);
        //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");

        return jsonPath;
    }

    public HashMap getWebSiteURL(int index) {
        HashMap map = new HashMap();
        String url = getGetWebAndAppsDetails().getString("[" + index + "].url");
        String time = getGetWebAndAppsDetails().getString("[" + index + "].totalTime");
        map.put("url",url);
        map.put("totalTime",TimeDateClass.convertSecondsToHHMMSSFormat(time));
        return map;
    }

    /*public String getWebsiteTitles() {
        return ;
    }*/

    public static void main(String[] args) throws IOException, ParseException {
        WebAndAppsEndPoints webapps = new WebAndAppsEndPoints();
        //System.out.println(webapps.getGetWebAndAppsDetails().);
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        System.out.println(webapps.getWebSiteURL(0).get("url"));
        System.out.println(webapps.getWebSiteURL(0).get("totalTime"));
    }
}
