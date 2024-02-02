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

public class FiveTilesDataEndPoint extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(FiveTilesDataEndPoint.class);
    public static final String SYSTEM_ACTIVITY_ENDPOINT = "GetTotalTimeInSecondsTilesData";

    /**
     * Build request specification for get five Tiles data.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForProductivityVsIdleAPI(HashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for TotalTimeInSeconds API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the TotalTimeInSeconds API with GET method and initialize the response object.
     */
    public void hitProductivityVsIdleAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(SYSTEM_ACTIVITY_ENDPOINT);
        endPoint = routes.getResource();
        LOGGER.info("Hit the TotalTimeInSeconds API with given request specification: " + reqSpec + "\n" +
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

    public JsonPath getTotalProductiveHoursByUserDetails(int day,String month,Object email,Object password) {
        LOGGER.info("Build request specification for TotalTimeInSeconds API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI(day,month,email,password);
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("userId", data.get("userId"));
            object.put("organizationId", data.get("organizationId"));
            object.put("fromDate", TimeDateClass.getCurrentDateWithHHMMSSTimeFormat());
            object.put("toDate", TimeDateClass.getCurrentDateWithHHMMSSTimeFormat());
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
    public HashMap<String, Object> getFiveTilesDataMap(int day,String month,Object email,Object password)
    {
        HashMap<String,Object> map=new HashMap<>();
        JsonPath tiles = getTotalProductiveHoursByUserDetails(day,month,email,password);
        map.put("name",tiles.getString("userName"));
        map.put("totalHours",TimeDateClass.convertSecondsToHHMMSS(tiles.getString("totalTime")));
        map.put("activeTime",TimeDateClass.convertSecondsToHHMMSS(tiles.getString("totalActiveTime")));
        map.put("idleTime",TimeDateClass.convertSecondsToHHMMSS(tiles.getString("totalIdleTime")));
        map.put("awayTime",TimeDateClass.convertSecondsToHHMMSS(tiles.getString("awayTime")));
        String[] first = tiles.getString("firstActivity").split(" ");
        map.put("firstActivity",TimeDateClass.convertTimeFormat(first[1]+" "+first[2],"h:mm:ss a", "hh:mm:ss a"));
        String[] last = tiles.getString("lastActivity").split(" ");
        map.put("lastActivity",TimeDateClass.convertTimeFormat(last[1]+" "+last[2],"h:mm:ss a", "hh:mm:ss a"));
        map.put("activePercent",tiles.getString("totalActivePercent")+"%");
        map.put("idlePercent",tiles.getString("totalIdlePercent")+"%");
        map.put("awayPercent",tiles.getString("awayTimePercent")+"%");
        map.put("timeZone",tiles.getString("timeZone"));
        map.put("date",tiles.getString("date").split(" ")[0]);
        return map;
    }

    public static void main(String[] args) throws IOException, ParseException {
        String email = JsonHelper.getValue("email1").toString();
        String password = JsonHelper.getValue("password1").toString();
        String month = JsonHelper.getValue("month").toString();
        FiveTilesDataEndPoint five=new FiveTilesDataEndPoint();
        System.out.println(five.getTotalProductiveHoursByUserDetails(16,month,email,password).getString(""));
        System.out.println(five.getFiveTilesDataMap(16,month,email,password));
    }
}
