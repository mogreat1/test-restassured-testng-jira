package tests.comment;

import org.testng.Assert;
import org.testng.annotations.*;

import base.TestBase;
import resources.AddBugPost;
import resources.AddCommentToBugPost;
import resources.DeleteBugDel;
import resources.DeleteCommentD;
import resources.LogoutD;

public class AddCommentToBugTests extends TestBase {

	AddCommentToBugPost addComToBug;
	DeleteBugDel dbd;
	AddBugPost abp;
	DeleteCommentD dcd;
	LogoutD lg;
	private String bugId;
	private String token;
	private String comId;

	@BeforeTest
	public void setUp() {
		abp = new AddBugPost();
		dbd = new DeleteBugDel();
		addComToBug = new AddCommentToBugPost();
		dcd = new DeleteCommentD();
		lg = new LogoutD();
		token = getToken();
		abp = new AddBugPost();
		bugId = abp.addBug(prop.getProperty("key"), token, "asdf", "adff");
	}

	@Test
	public void addCommentToBugTest() {
		String body = "Body of the comm";
		addComToBug.addCommentToBug(bugId, getToken(), body, "role", "Administrators", 201);
		comId = js.get("id");
		dcd.deleteComment(token, bugId, comId);
		Assert.assertEquals(js.get("body"), body);
		Assert.assertEquals(js.get("author.name"), prop.getProperty("username"));
	}

	@Test
	public void addCommentToNonExistingBugTest() {

		addComToBug.addCommentToBug("1234", getToken(), "body", "role", "Administrators", 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "Issue Does Not Exist");

	}

	@Test
	public void addCommentToUnauthorizedBugTest() {

		addComToBug.addCommentToBug(bugId, "asdf", "body", "role", "Administrators", 401);

	}

	@Test
	public void addCommentToBugWithoutBodyTest() {

		addComToBug.addCommentToBug(bugId, getToken(), "", "role", "Administrators", 400);
		Assert.assertEquals(js.get("errors.comment"), "Comment body can not be empty!");

	}

	@Test
	public void addCommentToBugWithoutTypeTest() {

		addComToBug.addCommentToBug(bugId, getToken(), "body", "", "Administrators", 400);
		Assert.assertTrue(js.get("errorMessages[0]").toString().contains("Can not construct instance of"));

	}

	@Test
	public void addCommentToBugWithoutVaueTest() {

		addComToBug.addCommentToBug(bugId, getToken(), "body", "role", "", 500);
		Assert.assertEquals(js.get("errorMessages[0]"), "Internal server error");

	}

	@AfterTest
	public void tearDown() {
		dbd.deleteBug(bugId, token);
		lg.logout(token);
	}

}
