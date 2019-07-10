package resources;

import static io.restassured.RestAssured.given;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetVersionG extends TestBase {
	public static String getVerstionRsr(String versionId) {
		return "rest/api/2/version/" + versionId;
	}

	public JsonPath getVetsion(String token, String versionId, int status) {

		Response resp = given().header("Cookie", token).when().get(getVerstionRsr(versionId)).then().assertThat()
				.statusCode(status).extract().response();
		return rawToJson(resp);

	}

}
