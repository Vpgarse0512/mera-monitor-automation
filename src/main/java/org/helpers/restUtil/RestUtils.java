package org.helpers.restUtil;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class RestUtils {
    private static final Logger LOGGER = LogManager.getLogger(RestUtils.class);
    public static long responseTimeInSeconds;
    public static Response response;
    public static JsonPath jsonPath;
    public static RequestSpecification reqSpec;
    public final String DATE_FORMAT = "dd-MM-yyyy";
    private static LocalDateTime resDateTime;

    public static RequestSpecification requestSpecification(Map<String, ?> body,
                                                            Map<String, ?> token) {
        RequestSpecification reqSpec;
        PrintStream log = null;
        try {
            log = new PrintStream(new FileOutputStream("logs/api_logs.txt"));
        } catch (IOException e) {
            LOGGER.info(e);
        }
        reqSpec = RestAssured.given()
                .baseUri(ResourcePath.QA_BASE_URI)
                .contentType(ContentType.JSON)
                .headers(token)
                .body(body);
        return reqSpec;
    }
    public static RequestSpecification requestSpecification (LinkedHashMap<String, Object> queryParams, Map<String, ?> token) {
        RequestSpecification reqSpec;
        PrintStream log = null;
        try {
            log = new PrintStream (new FileOutputStream ("logs/api_logs.txt"));
        } catch (IOException e) {
            LOGGER.info (e);
        }
        reqSpec = RestAssured.given ()
                .baseUri (ResourcePath.QA_BASE_URI)
                .contentType(ContentType.JSON)
                .headers(token)
                .queryParams (queryParams);
        return reqSpec;
    }


    public static String getJsonPath(Response response, String key) {
        String value = null;
        try {
            String resp = response.asPrettyString();
            JsonPath js = new JsonPath(resp);
            value = js.get(key).toString();
        } catch (NullPointerException e) {
            LOGGER.info(e);
        }
        return value;
    }

    /**
     * This method is used to read data from JSON
     *
     * @param path
     * @return
     * @author Rahul.Rana
     */
    public static String generateStringFromResource(String path) {
        String resource = null;
        try {
            resource = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return resource;
    }

    public static JsonPath rawToJson(Response response) {
        String asString = response.asPrettyString();
        return new JsonPath(asString);
    }

    /**
     * This method is used to get the response of the API
     *
     * @param reqSpec
     * @param methodType
     * @param apiRoute
     * @return
     */
    public static Response getResponse(RequestSpecification reqSpec, String methodType, String apiRoute) {
        Response response = null;
        LocalDateTime now;
        ValidatableResponse valRes;
        switch (methodType) {
            case "POST":
                response = RestAssured.given().spec(reqSpec).when().post(apiRoute);
                now = LocalDateTime.now();
                setDateTimeOfResponse(now);
                valRes = response.then();
                responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
                break;
            case "GET":
                response = RestAssured.given().spec(reqSpec).when().get(apiRoute);
                //LOGGER.info(apiRoute);
                now = LocalDateTime.now();
                setDateTimeOfResponse(now);
                valRes = response.then();
                responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
                break;
            case "PUT":
                response = RestAssured.given().spec(reqSpec).when().put(apiRoute);
                LOGGER.info(apiRoute);
                now = LocalDateTime.now();
                setDateTimeOfResponse(now);
                valRes = response.then();
                responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
                break;
            case "DELETE":
                response = RestAssured.given().spec(reqSpec).when().delete(apiRoute);
                LOGGER.info(apiRoute);
                now = LocalDateTime.now();
                setDateTimeOfResponse(now);
                valRes = response.then();
                responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
                break;
        }
        return response;
    }

    public static void pause(int miliSec) {
        try {
            Thread.sleep(miliSec);
        } catch (Exception e) {
            LOGGER.info(e);
        }
    }

    public String getDateTimeOfResponse() {
        return resDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static void setDateTimeOfResponse(LocalDateTime dateTimeOfResponse) {
        resDateTime = dateTimeOfResponse;
    }
}
