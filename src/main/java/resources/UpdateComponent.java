package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateComponent extends TestBase {

	public static String updateComponentRsr(String componentId) {
		return "/rest/api/2/component/" + componentId;
	}

	public static String updateComponentPL(String name, String description, String leadUser, String key) {
		String updateComponentPL = "{\r\n" + "    \"name\": \"" + name + "\",\r\n" + "    \"description\": \""
				+ description + "\",\r\n" + "    \"leadUserName\": \"" + leadUser + "\",\r\n"
				+ "    \"assigneeType\": \"PROJECT_LEAD\",\r\n" + "    \"isAssigneeTypeValid\": false,\r\n"
				+ "    \"project\": \"" + key + "\",\r\n" + "    \"projectId\": 10000\r\n" + "}";

		return updateComponentPL;
	}

	public JsonPath updateComponent(String token, String componentId, String name, String description, String leadUser,
			String key, int status) {

		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(updateComponentPL(name, description, leadUser, key)).when().put(updateComponentRsr(componentId))
				.then().assertThat().statusCode(status).extract().response();
		return rawToJson(resp);

	}

}
