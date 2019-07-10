package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddCommentToBugPost extends TestBase {

	public static String addCommentToBugRsr(String bugId) {
		String addCommentToBugRsr = "/rest/api/2/issue/" + bugId + "/comment";
		return addCommentToBugRsr;
	}

	public static String addCommentToBugPL(String body, String type, String value) {
		String addCommentToBugPL = "{\r\n" + "    \"body\": \"" + body + "\",\r\n" + "    \"visibility\": {\r\n"
				+ "        \"type\": \"" + type + "\",\r\n" + "        \"value\": \"" + value + "\"\r\n" + "    }\r\n"
				+ "}";
		return addCommentToBugPL;

	}

	public JsonPath addCommentToBug(String bugId, String token, String body, String type, String value, int status) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addCommentToBugPL(body, type, value)).when().post(addCommentToBugRsr(bugId)).then().assertThat()
				.statusCode(status).extract().response();

		js = rawToJson(resp);
		return js;
	}

	public String addCommentToBug(String bugId, String token, String body) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addCommentToBugPL(body, "role", "Administrators")).when().post(addCommentToBugRsr(bugId)).then()
				.extract().response();

		js = rawToJson(resp);
		return js.get("id");
	}

}
