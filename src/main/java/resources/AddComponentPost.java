package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddComponentPost extends TestBase {

	public static String createComponentRsr() {
		return "/rest/api/2/component";
	}

	public static String createComponentPL(String name, String userName, String key) {
		String createComponentPL = "{\r\n" + "    \"name\": \"" + name + "\",\r\n"
				+ "    \"description\": \"This is a JIRA componen\",\r\n" + "    \"leadUserName\": \"" + userName
				+ "\",\r\n" + "    \"assigneeType\": \"PROJECT_LEAD\",\r\n" + "    \"isAssigneeTypeValid\": false,\r\n"
				+ "    \"project\": \"" + key + "\",\r\n" + "    \"projectId\": 10001\r\n" + "}";

		return createComponentPL;

	}

	public JsonPath createComponent(String token, String name, String userName, String key, int status) {

		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(createComponentPL(name, userName, key)).when().post(createComponentRsr()).then().assertThat()
				.statusCode(status).extract().response();
		js = rawToJson(resp);
		return js;

	}

	public String createComponentId(String token, String name, String userName, String key, int status) {

		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(createComponentPL(name, userName, key)).when().post(createComponentRsr()).then().assertThat()
				.statusCode(status).extract().response();
		js = rawToJson(resp);

		return js.get("id");

	}

}
