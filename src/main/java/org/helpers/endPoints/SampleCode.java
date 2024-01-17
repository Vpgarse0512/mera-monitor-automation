package org.helpers.endPoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.helpers.jsonReader.JsonHelper;
import org.json.simple.parser.ParseException;
import org.propertyHelper.PropertiesUtils;
import org.propertyHelper.PropertyFileEnum;
import org.timeUtil.TimeDateClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;

public class SampleCode {
    public static void main(String[] args) throws IOException, ParseException {
        LinkedHashMap<String,Object> body = new LinkedHashMap<>();
        body.put("userId", "6569acca31022f1192c842dc");
        body.put("organizationId", "656980bd31022f1192c8387f");
        body.put("fromDate", TimeDateClass.getCustomDate(8));
        body.put("toDate",TimeDateClass.getCustomDate(9));
        RestAssured.baseURI = "https://timapi.aapnainfotech.com/api";
        RestAssured.basePath="/v1/Dashboard/GetTotalProductiveHoursByUser";
        System.out.println(body);
        Response returnGivenData = given().
                contentType(ContentType.JSON).
                headers("Authorization", "Bearer "+PropertiesUtils.getProperty(PropertyFileEnum.GLOB, "accessToken")).
                body(body).
                when().post();
        System.out.println(returnGivenData.getStatusCode());
        System.out.println(returnGivenData.getBody().asString());
        System.out.println(returnGivenData.jsonPath().getString("userName"));
    }
}