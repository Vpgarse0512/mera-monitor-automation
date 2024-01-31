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
import java.util.List;

public class HolidayEndpoints extends RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(HolidayEndpoints.class);
    public static final String HOLIDAY_ENDPOINT = "GetYearlyHolidaysByOrgId";

    /**
     * Build request specification for Get User Time To Claim.
     *
     * @param payloadObj,cred
     */
    public void buildRequestSpecForGetHolidayListPI(LinkedHashMap<String, Object> payloadObj, Object token) {
        HashMap<String, Object> accessToken = new HashMap();
        accessToken.put("Authorization", token); // accessToken
        LOGGER.info("Building request specification for ClaimTimeForUser API.");
        reqSpec = RestUtils.requestSpecification(payloadObj, accessToken);
    }

    /**
     * Execute the Get User Time To Claim API with GET method and initialize the response object.
     */
    public void hitGetHolidayListAPI() {
        String endPoint;
        Routes routes = Routes.valueOf(HOLIDAY_ENDPOINT);
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

    public JsonPath getHolidayList() {
        LOGGER.info("Build request specification for Get User Time To Claim API.");
        String accessToken = "Bearer " + PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken");
        LoginEndPoints login = new LoginEndPoints();
        LinkedHashMap<String, Object> data = null;
        try {
            data = login.getTheDetailsFromLoginAPI();
            LinkedHashMap<String, Object> object = new LinkedHashMap<>();
            object.put("organizationId", data.get("organizationId"));
            object.put("currentDate", TimeDateClass.getTodaysDate());
            buildRequestSpecForGetHolidayListPI(object, accessToken);
            hitGetHolidayListAPI();
            Assert.assertEquals(getAPIResponseCode(), 200);
            //Assert.assertEquals(getLoginAPIResponseMessage(), "Login Successful");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonPath;
    }

    public LinkedHashMap<String, List<String>> getHolidayData() {
        JsonPath holiday = getHolidayList();
        int size = getHolidayList().getList("").size();
        //System.out.println(size);
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        String[] list = {"holidayName", "holidayDate"};
        for (String key : list) {
            List<String> cellData = new java.util.ArrayList<>();
            for (int i = 0; i < size - 1; i++) {

                cellData.add(holiday.get("[" + i + "]." + key + ""));
            }
            map.put(key, cellData);

        }
        return map;
    }

    public static void main(String[] args) {
        HolidayEndpoints holiday = new HolidayEndpoints();
        //System.out.println(holiday.getHolidayList().getString(""));
        System.out.println(holiday.getHolidayData());
    }
}
