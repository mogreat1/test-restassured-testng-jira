package tests.version;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.TestBase;
import resources.AddVersionPost;
import resources.DeleteVersionD;

public class DeleteVersionTests extends TestBase {

	AddVersionPost avp;
	DeleteVersionD dvd;

	private String token;
	private String versionId;
	private String name;

	@BeforeTest
	public void setUp() {
		avp = new AddVersionPost();
		token = getToken();
		dvd = new DeleteVersionD();

	}

	@BeforeMethod
	public void setValues() {
		name = "some excellent name";
		versionId = avp.addVersion(token, name);

	}

	@Test
	public void deleteVersTest() {

		dvd.deleteVersion(token, versionId, 204);

	}

	@Test
	public void deleteDeletedVersTest() {

		dvd.deleteVersion(token, versionId);
		js = dvd.deleteVersion(token, versionId, 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "Could not find version for id '" + versionId + "'");

	}

	@AfterMethod
	public void tearDown() {
		dvd.deleteVersion(token, versionId);
	}

}
