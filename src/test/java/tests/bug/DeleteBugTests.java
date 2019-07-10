package tests.bug;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.TestBase;
import resources.AddBugPost;
import resources.DeleteBugDel;
import resources.LogoutD;

public class DeleteBugTests extends TestBase {
	DeleteBugDel dbd;
	AddBugPost abp;
	LogoutD lg;
	private String bugId;
	private String token;
	
	@BeforeTest
	public void setUp() {
		dbd = new DeleteBugDel();
		abp = new AddBugPost();
		lg = new LogoutD();
		token = getToken();
	}

	@BeforeMethod
	public void addBug() {
		bugId = abp.addBug(prop.getProperty("key"), token, "summary1", "desc");

	}

	@Test
	public void deleteBugTest() {

		dbd.deleteBug(bugId, token, 204);

	}

	@Test
	public void deleteDeletedBugTest() {
		dbd.deleteBug(bugId, token);
		dbd.deleteBug(bugId, getToken(), 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "Issue Does Not Exist");

	}
	
	@AfterTest
	public void tearDown() {
		lg.logout(token);
	}

}
