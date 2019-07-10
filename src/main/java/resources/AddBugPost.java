package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddBugPost extends TestBase {

	public static String addBugRsr() {
		return "/rest/api/2/issue";
	}

	public static String addBugPL(String key, String summary, String description, String issueType) {
		String addBugPL = "{\r\n" + "	\"fields\": {\r\n" + "		\"project\":\r\n" + "		{\r\n"
				+ "			\"key\": \"" + key + "\"\r\n" + "		},\r\n" + "		\"summary\": \"" + summary
				+ "\",\r\n" + "		\"description\": \"" + description + "\",\r\n" + "		\"issuetype\": {\r\n"
				+ "			\"name\": \"" + issueType + "\"\r\n" + "		}\r\n" + "\r\n" + "	}\r\n" + "}";
		return addBugPL;
	}

	public JsonPath addBug(String key, String token, String summary, String description, String issueType, int status) {

		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addBugPL(key, summary, description, issueType)).when().post(addBugRsr()).then().assertThat()
				.statusCode(status).extract().response();
		js = rawToJson(resp);
		return js;

	}

	public String addBug(String key, String token, String summary, String description) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addBugPL(key, summary, description, "Bug")).when().post(addBugRsr()).then().extract()
				.response();
		js = rawToJson(resp);
		return js.get("id");
	}

}
