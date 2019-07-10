package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteComponentDel extends TestBase {

	public static String deleteComponentRsr(String componentId) {
		return "/rest/api/2/component/" + componentId;
	}

	public JsonPath deleteComponent(String token, String componentId, int status) {
		Response resp = given().header("Cookie", token).when().delete(deleteComponentRsr(componentId)).then().assertThat()
				.statusCode(status).extract().response();
		js = rawToJson(resp);
		return js;
	}

}
