package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteBugDel extends TestBase {

	public static String deleteBugRsr(String bugId) {
		String deleteBugRsr = "/rest/api/2/issue/" + bugId;
		return deleteBugRsr;
	}

	public JsonPath deleteBug(String bugId, String token, int status) {

		Response resp = given().header("Cookie", token).delete(deleteBugRsr(bugId)).then().assertThat()
				.statusCode(status).extract().response();
		js = rawToJson(resp);
		return js;

	}

	public void deleteBug(String bugId, String token) {

		given().header("Cookie", token).delete(deleteBugRsr(bugId));

	}

}
