package tests.component;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import io.restassured.path.json.JsonPath;
import resources.AddComponentPost;
import resources.DeleteComponentDel;

public class AddComponentTests extends TestBase {
	private AddComponentPost ccp;
	private DeleteComponentDel ccd;
	private String componentId;
	private String token;

	@BeforeTest
	private void setUp() {
		ccp = new AddComponentPost();
		ccd = new DeleteComponentDel();
		token = getToken();

	}

	@Test
	private void createComponentTest() {
		String name = "A new component name";
		JsonPath js = ccp.createComponent(token, name, prop.getProperty("username"), prop.getProperty("key"), 201);
		componentId = js.get("id");
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("name"), name);
		sa.assertEquals(js.get("lead.key"), prop.getProperty("username"));
		sa.assertEquals(js.get("project"), prop.getProperty("key"));
		sa.assertAll();

		ccd.deleteComponent(token, componentId, 204);

	}

	@Test
	private void createComponentNonExstingProjectTest() {
		JsonPath js = ccp.createComponent(token, "name", prop.getProperty("username"), "xxx", 404);
		Assert.assertTrue(js.get("errorMessages[0]").toString().contains("No project could be found with key"));

	}

	@Test
	private void createComponentNonExstingUserTest() {
		JsonPath js = ccp.createComponent(token, "names", prop.getProperty("username") + 1, prop.getProperty("key"),
				400);
		Assert.assertTrue(js.get("errors.leadUserName").toString()
				.contains("The user " + prop.getProperty("username") + 1 + " does not exist."));

	}

	@Test
	private void createComponentEmptyNameTest() {
		JsonPath js = ccp.createComponent(token, "", prop.getProperty("username"), prop.getProperty("key"), 400);
		Assert.assertEquals(js.get("errors.name"),
				"The component name specified is invalid - cannot be an empty string.");

	}

}
