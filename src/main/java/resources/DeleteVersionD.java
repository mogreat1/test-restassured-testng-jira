package resources;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class DeleteVersionD extends TestBase {

	public static String deleteVerstionRsr(String versionId) {
		return "rest/api/2/version/" + versionId;
	}

	public JsonPath deleteVersion(String token, String versionId, int status) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token).when()
				.delete(deleteVerstionRsr(versionId)).then().assertThat().statusCode(status).extract().response();
		return rawToJson(resp);
	}

	public JsonPath deleteVersion(String token, String versionId) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token).when()
				.delete(deleteVerstionRsr(versionId)).then().extract().response();
		return rawToJson(resp);
	}

}
