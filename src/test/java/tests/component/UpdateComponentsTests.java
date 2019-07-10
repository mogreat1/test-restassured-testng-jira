package tests.component;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import resources.AddComponentPost;
import resources.DeleteComponentDel;
import resources.UpdateComponent;

public class UpdateComponentsTests extends TestBase {
	AddComponentPost acp;
	UpdateComponent uc;
	DeleteComponentDel dcd;
	String componentId;
	String token;
	String compName;
	String descr;
	String key2;

	@BeforeTest
	public void setUp() {
		acp = new AddComponentPost();
		uc = new UpdateComponent();
		token = getToken();
		dcd = new DeleteComponentDel();

	}

	@BeforeMethod
	public void createComponent() {

		componentId = acp.createComponentId(token, "component n", prop.getProperty("username"), prop.getProperty("key"),
				201);
	}

	@Test
	public void updateComponentTest() {
		compName = "new name";
		descr = "new descr";
		key2 = prop.getProperty("key2");
		js = uc.updateComponent(token, componentId, compName, descr, prop.getProperty("username"), key2, 200);
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("name"), compName);
		sa.assertEquals(js.get("description"), descr);
		// sa.assertEquals(js.get("project"), key2); -bag
		sa.assertAll();

	}

	@Test
	public void updateToEmptyNameTest() {
		js = uc.updateComponent(token, componentId, "", "desc", prop.getProperty("username"), key2, 400);
		Assert.assertEquals(js.get("errors.name"),
				"The component name specified is invalid - cannot be an empty string.");
	}

	@Test
	public void updateToEmptyDescTest() {
		js = uc.updateComponent(token, componentId, "namee", "desc", prop.getProperty("username"), key2, 200);
		Assert.assertTrue(js.get("errors.description") == null);
	}

	@Test
	public void updateToNonExistingUserNameTest() {
		String userName = prop.getProperty("username") + 1;
		js = uc.updateComponent(token, componentId, "namee", "desc", userName, key2, 400);
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("errors.leadUserName"), "The user " + userName + " does not exist.");
		sa.assertEquals(js.get("errors.componentLead"), "The user " + userName + " does not exist.");
		sa.assertAll();

	}
	
	@Test
	public void updateNonExistingComponentTest() {
		String wrongCompId = componentId+1;
		js = uc.updateComponent(token, wrongCompId, "namee", "desc", prop.getProperty("username"), key2, 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "The component with id "+wrongCompId+" does not exist.");
		

	}



	@AfterMethod
	public void tearDown() {
		dcd.deleteComponent(token, componentId, 204);
	}

}
