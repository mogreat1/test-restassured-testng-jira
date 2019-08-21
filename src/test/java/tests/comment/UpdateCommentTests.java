package tests.comment;

import org.testng.Assert;
import org.testng.annotations.*;

import base.TestBase;
import resources.AddBugPost;
import resources.AddCommentToBugPost;
import resources.AddComponentPost;
import resources.DeleteBugDel;
import resources.DeleteCommentD;
import resources.LogoutD;
import resources.UpdateCommentPut;

public class UpdateCommentTests extends TestBase {
	private UpdateCommentPut updateComm;
	private DeleteCommentD dcd;
	private AddBugPost abp;
	private AddCommentToBugPost addComm;
	private DeleteBugDel dbd;
	private LogoutD lg;

	private String bugId;
	private String commentId;
	private String token;

	@BeforeTest
	private void setUp() {
		token = getToken();

		abp = new AddBugPost();
		addComm = new AddCommentToBugPost();
		updateComm = new UpdateCommentPut();
		dcd = new DeleteCommentD();
		dbd = new DeleteBugDel();
		lg = new LogoutD();

		bugId = abp.addBug(prop.getProperty("key"), token, "summary asdf", "adf");

	}

	@BeforeMethod
	private void addComment() {
		commentId = addComm.addCommentToBug(bugId, token, "bodyOfTheComment");
	}

	@Test
	private void updateCommentTest() {
		String body = "updating existing comment";
		updateComm.updateComment(bugId, commentId, token, body, "role", "Administrators", 200);
		Assert.assertEquals(js.get("body"), body);

	}

	@Test
	private void updateCommentForNonExistingBugTest() {

		updateComm.updateComment("1234", commentId, token, "updating existing comment", "role", "Administrators", 404);
		Assert.assertEquals(js.get("errorMessages[0]"), "Issue Does Not Exist");

	}

	@Test
	private void updateNonExistingCommentTest() {
		String invalidCommentId = "1234";
		updateComm.updateComment(bugId, invalidCommentId, token, "updating existing comment", "role", "Administrators",
				404);
		Assert.assertEquals(js.get("errorMessages[0]"), "Can not find a comment for the id: " + invalidCommentId + ".");

	}

	@Test
	private void updateCommentUnathorizedTest() {
		updateComm.updateComment(bugId, commentId, "asdf", "updating existing comment", "role", "Administrators", 401);

	}

	@Test
	private void updateCommentWithoutBodyTest() {

		updateComm.updateComment(bugId, commentId, getToken(), "", "role", "Administrators", 400);
		Assert.assertEquals(js.get("errors.comment"), "Comment body can not be empty!");

	}

	@Test
	private void updateCommentWithoutTypeTest() {

		updateComm.updateComment(bugId, commentId, getToken(), "body update", "", "Administrators", 400);
		Assert.assertTrue(js.get("errorMessages[0]").toString().contains("Can not construct instance of"));

	}

	@Test
	private void updateCommentWithoutValueTest() {

		updateComm.updateComment(bugId, commentId, getToken(), "body update", "role", "", 500);
		Assert.assertTrue(js.get("errorMessages[0]").toString().contains("Internal server error"));

	}

	@AfterMethod
	private void deleteComm() {
		dcd.deleteComment(token, bugId, commentId);
	}

	@AfterTest
	private void tearDown() {
		dbd.deleteBug(bugId, token);
		lg.logout(token);

	}

}
