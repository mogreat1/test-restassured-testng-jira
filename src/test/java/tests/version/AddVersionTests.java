package tests.version;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import resources.AddVersionPost;
import resources.DeleteVersionD;

public class AddVersionTests extends TestBase {
	AddVersionPost avp;
	DeleteVersionD dvd;

	private String token;
	private String versionId;
	private String desc;
	private String name;
	private String data;

	@BeforeTest
	public void setUp() {
		avp = new AddVersionPost();
		token = getToken();
		dvd = new DeleteVersionD();

	}

	@BeforeMethod
	public void setValues() {
		desc = "excellent version";
		name = "awesomee!";
		data = "2019-01-31";
	}

	@Test
	public void addVersTest() {

		js = avp.addVersion(token, desc, name, data, prop.getProperty("key"), prop.getProperty("projectId"), 201);
		versionId = js.get("id");

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("description"), desc);
		sa.assertEquals(js.get("name"), name);
		sa.assertEquals(js.get("releaseDate"), data);
		sa.assertAll();

	}

	@Test
	public void addVersWithoutDescTest() {
		desc = "";

		js = avp.addVersion(token, desc, name, data, prop.getProperty("key"), prop.getProperty("projectId"), 201);
		versionId = js.get("id");

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(js.get("description"), null);
		sa.assertEquals(js.get("name"), name);
		sa.assertEquals(js.get("releaseDate"), data);
		sa.assertAll();

	}

	@Test
	public void addVersWithoutNameTest() {
		name = "";
		js = avp.addVersion(token, desc, name, data, prop.getProperty("key"), prop.getProperty("projectId"), 400);
		versionId = js.get("id");

		Assert.assertEquals(js.get("errors.name"), "You must specify a valid version name");

	}

	@Test
	public void addVersWithoutDataTest() {
		data = "";
		js = avp.addVersion(token, desc, name, data, prop.getProperty("key"), prop.getProperty("projectId"), 400);
		versionId = js.get("id");

		Assert.assertTrue(js.get("errorMessages[0]").toString().contains("Unable to unmarshal"));

	}

	@Test
	public void addVersWithoutProjectSpecifiedTest() {
		js = avp.addVersion(token, desc, name, data, "", null, 400);
		versionId = js.get("id");

		Assert.assertEquals(js.get("errorMessages[0]"), "Project must be specified to create a version.");

	}

	@Test
	public void addVersWithExistingNameTest() {

		versionId = avp.addVersion(token, name);

		js = avp.addVersion(token, desc, name, data, prop.getProperty("key"), prop.getProperty("projectId"), 400);
		Assert.assertEquals(js.get("errors.name"), "A version with this name already exists in this project.");

	}

	@AfterMethod
	public void tearDown() {
		dvd.deleteVersion(token, versionId);
	}

}
