package tests.component;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import resources.AddComponentPost;
import resources.DeleteComponentDel;
import resources.GetComponentGet;

public class GetComponentTests extends TestBase {

	private AddComponentPost acp;
	private GetComponentGet gcg;
	private String token;
	private String componentId;

	@BeforeTest
	private void setUp() {
		acp = new AddComponentPost();
		token = getToken();
		gcg = new GetComponentGet();

	}

	@BeforeMethod
	private void createComponent() {
		componentId = acp.createComponentId(token, "somename", prop.getProperty("username"), prop.getProperty("key"),
				201);
	}

	@Test
	private void getComponentTest() {
		js = gcg.getComponent(token, componentId, 200);
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("project"), prop.getProperty("key"));
		sa.assertEquals(js.get("lead.name"), prop.getProperty("username"));
		sa.assertAll();
	}

	@Test
	private void getNonExistingComponentTest() {
		String wrongCompId = componentId + 1;
		js = gcg.getComponent(token, wrongCompId, 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "The component with id " + wrongCompId + " does not exist.");

	}

	@AfterMethod
	private void tearDown() {
		new DeleteComponentDel().deleteComponent(token, componentId, 204);
	}

}
