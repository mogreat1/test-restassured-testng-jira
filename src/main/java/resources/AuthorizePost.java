package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AuthorizePost extends TestBase {

	public static String AuthorizeRsr() {
		String authorizeRsr = "/rest/auth/1/session";
		return authorizeRsr;
	}

	public static String AuthorizePL(String username, String password) {
		String authorizePL = "{\r\n" + "    \"username\": \"" + username + "\",\r\n" + "    \"password\": \"" + password
				+ "\"\r\n" + "}";
		return authorizePL;
	}

	public JsonPath authorizePost(String username, String password, int expectedStatusCode) {

		Response resp = given().header("Content-Type", "application/json").body(AuthorizePL(username, password)).when()
				.post(AuthorizeRsr()).then().assertThat().statusCode(expectedStatusCode).extract().response();
		js = rawToJson(resp);
		return js;

	}


}
