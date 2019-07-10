package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LogoutD extends TestBase {

	public static String logoutRsr() {
		return "/rest/auth/1/session";
	}

	public JsonPath logout(String token, int status) {

		Response resp = given().header("Cookie", token).when().delete(logoutRsr()).then().assertThat()
				.statusCode(status).extract().response();
		return rawToJson(resp);

	}

	public void logout(String token) {

		given().header("Cookie", token).when().delete(logoutRsr());

	}

}
