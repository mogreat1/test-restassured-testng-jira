package resources;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AddVersionPost extends TestBase {

	public static String addVerstionRsr() {
		return "rest/api/2/version/";
	}

	public static String addVersionPL(String desc, String name, String data, String key, String projectId) {
		return "{\r\n" + "    \"description\": \"" + desc + "\",\r\n" + "    \"name\": \"" + name + "\",\r\n"
				+ "    \"archived\": false,\r\n" + "    \"released\": true,\r\n" + "    \"releaseDate\": \"" + data
				+ "\",\r\n" + "    \"project\": \"" + key + "\",\r\n" + "    \"projectId\": " + projectId + "\r\n"
				+ "}";
	}

	public JsonPath addVersion(String token, String desc, String name, String data, String key, String projectId,
			int status) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addVersionPL(desc, name, data, key, projectId)).when().post(addVerstionRsr()).then().assertThat()
				.statusCode(status).extract().response();
		return rawToJson(resp);
	}

	public String addVersion(String token, String name) {
		Response resp = given().headers("Content-Type", "application/json", "Cookie", token)
				.body(addVersionPL("descr", name, "2019-01-31", prop.getProperty("key"), prop.getProperty("projectId")))
				.when().post(addVerstionRsr()).then().extract().response();
		js = rawToJson(resp);
		return js.get("id");
	}

}
