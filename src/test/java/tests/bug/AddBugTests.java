package tests.bug;

import org.testng.Assert;
import org.testng.annotations.*;

import base.TestBase;
import resources.AddBugPost;
import resources.DeleteBugDel;
import resources.LogoutD;

public class AddBugTests extends TestBase {
	private AddBugPost abp;
	private DeleteBugDel dbd;
	private LogoutD lg;
	private String bugId;
	private String token;

	@BeforeTest
	private void setUp() {
		abp = new AddBugPost();
		dbd = new DeleteBugDel();
		lg = new LogoutD();
		token = getToken();
	}

	@Test(enabled = true)
	private void addBugTest() {

		js = abp.addBug(prop.getProperty("key"), token, "wonderful bug", "asdf", "Bug", 201);
		bugId = js.get("id");

	}

	@Test(enabled = true)
	private void emptyKeyTest() {

		js = abp.addBug("", getToken(), "Issue test", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.project"), "project is required");

	}

	@Test(enabled = true)
	private void nonExistingKeyTest() {

		js = abp.addBug("ASD", getToken(), "Issue test", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.project"), "project is required");

	}

	@Test(enabled = true)
	private void emptySummaryTest() {

		js = abp.addBug(prop.getProperty("key"), getToken(), "", "some new issue", "Bug", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.summary"), "You must specify a summary of the issue.");

	}

	@Test(enabled = true)
	private void emptyIssueTypeTest() {

		abp.addBug(prop.getProperty("key"), getToken(), "Issue test", "Description of the issue", "", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.issuetype"), "issue type is required");

	}

	@Test(enabled = true)
	private void wrongIssueTypeTest() {

		abp.addBug(prop.getProperty("key"), getToken(), "Issue test", "Description of the issue", "Bugg", 400);
		bugId = js.get("id");
		Assert.assertEquals(js.get("errors.issuetype"), "issue type is required");

	}

	@AfterMethod
	private void DeleteBug() {
		dbd.deleteBug(bugId, token);
	}

	@AfterTest
	private void tearDown() {
		lg.logout(token);
	}

}
