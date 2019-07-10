package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.AddCommentToBugPost;
import resources.AuthorizePost;
import resources.LogoutD;
import resources.UpdateCommentPut;

public class TestBase {

	public static Properties prop;
	public static JsonPath js;
	public static Logger log;

	public TestBase() {
		prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("../JiraTest/variables.properties");
			prop.load(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RestAssured.baseURI = prop.getProperty("HOST");
		log = LogManager.getLogger();
	}

	public static JsonPath rawToJson(Response response) {

		String stringResp = response.asString();
		JsonPath jsonResp = new JsonPath(stringResp);
		log.info("Raw Respose was converted to JSON " + jsonResp.toString());
		return jsonResp;

	}

	public String getToken() {
		AuthorizePost ap = new AuthorizePost();
		js = ap.authorizePost(prop.getProperty("username"), prop.getProperty("password"), 200);
		log.info("Reponse for Authorization is 200 ");
		String value = js.get("session.value");
		String token = "JSESSIONID=" + value;
		log.info("Token for given authorization is: " + token);
		return token;
	}


}
