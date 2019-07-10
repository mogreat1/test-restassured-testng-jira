package tests.bug;

import org.testng.Assert;
import org.testng.annotations.*;

import base.TestBase;
import resources.AddBugPost;
import resources.DeleteBugDel;
import resources.LogoutD;

public class AddBugTests extends TestBase {
	AddBugPost abp;
	DeleteBugDel dbd;
	LogoutD lg;
	private String bugId;
	private String token;

	@BeforeTest
	public void setUp() {
		abp = new AddBugPost();
		dbd = new DeleteBugDel();
		lg = new LogoutD();
		token = getToken();
	}

	@Test(enabled = true)
	public void addBugTest() {

		js = abp.addBug(prop.getProperty("key"), token, "wonderful bug", "asdf", "Bug", 201);
		bugId = js.get("id");

	}

	@Test(enabled = true)
	public void emptyKeyTest() {

		js = abp.addBug("", getToken(), "Issue test", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.project"), "project is required");

	}

	@Test(enabled = true)
	public void nonExistingKeyTest() {

		js = abp.addBug("ASD", getToken(), "Issue test", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.project"), "project is required");

	}

	@Test(enabled = true)
	public void emptySummaryTest() {

		js = abp.addBug(prop.getProperty("key"), getToken(), "", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.summary"), "You must specify a summary of the issue.");

	}

	@Test(enabled = true)
	public void emptyIssueTypeTest() {

		abp.addBug(prop.getProperty("key"), getToken(), "Issue test", "Description of the issue", "", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.issuetype"), "issue type is required");

	}

	@Test(enabled = true)
	public void wrongIssueTypeTest() {

		abp.addBug(prop.getProperty("key"), getToken(), "Issue test", "Description of the issue", "Bugg", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.issuetype"), "issue type is required");

	}

	@AfterMethod
	public void DeleteBug() {
		dbd.deleteBug(bugId, token);
	}

	@AfterTest
	public void tearDown() {
		lg.logout(token);
	}

}
