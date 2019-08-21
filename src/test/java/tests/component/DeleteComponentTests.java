package tests.component;

import org.testng.Assert;
import org.testng.annotations.*;

import base.TestBase;
import resources.AddComponentPost;
import resources.DeleteComponentDel;

public class DeleteComponentTests extends TestBase {
	private AddComponentPost ccp;
	private DeleteComponentDel dcd;
	private String token;
	private String componentId;
	private String deletedComponentId;

	@BeforeTest
	private void setUp() {
		ccp = new AddComponentPost();
		dcd = new DeleteComponentDel();
		token = getToken();
	}

	@BeforeMethod
	private void createComponent() {
		componentId = ccp.createComponentId(token, "some namess", prop.getProperty("username"), prop.getProperty("key"),
				201);

	}

	@Test
	private void deleteComponentTest() {
		dcd.deleteComponent(token, componentId, 204);
		deletedComponentId = componentId;
	}

	@Test(dependsOnMethods = "deleteComponentTest", enabled = true)
	private void deleteDeletedComponentTest() {
		js = dcd.deleteComponent(token, deletedComponentId, 404);
		Assert.assertEquals(js.get("errorMessages[0]"),
				"The component with id " + deletedComponentId + " does not exist.");
		dcd.deleteComponent(token, componentId, 204);
	}

}
