package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateCommentPut extends TestBase {

	public static String updateCommentRsr(String bugId, String commentId) {

		String updateCommmentRsr = "/rest/api/2/issue/" + bugId + "/comment/" + commentId;
		return updateCommmentRsr;

	}

	public static String updateCommentPL(String body, String type, String value) {
		String updateCommentPL = "{\r\n" + "    \"body\": \"" + body + "\",\r\n" + "    \"visibility\": {\r\n"
				+ "        \"type\": \"" + type + "\",\r\n" + "        \"value\": \"" + value + "\"\r\n" + "    }\r\n"
				+ "}";
		return updateCommentPL;

	}

	public JsonPath updateComment(String bugId, String commentId, String token, String body, String type,
			String value, int status) {

		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(updateCommentPL(body, type, value)).when().put(updateCommentRsr(bugId, commentId)).then()
				.assertThat().statusCode(status).extract().response();
		js = rawToJson(resp);
		return js;

	}
}
