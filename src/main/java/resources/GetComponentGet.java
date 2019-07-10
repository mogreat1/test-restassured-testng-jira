package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetComponentGet extends TestBase {

	public static String getComponentRsr(String componentID) {
		return "/rest/api/2/component/" + componentID;
	}

	public JsonPath getComponent(String token, String componentID, int status) {
		Response resp = given().headers("Cookie", token).when().get(getComponentRsr(componentID)).then().assertThat()
				.statusCode(status).extract().response();
		return rawToJson(resp);
	}

}
