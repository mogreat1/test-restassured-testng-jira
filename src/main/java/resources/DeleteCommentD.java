package resources;

import base.TestBase;
import static io.restassured.RestAssured.given;

public class DeleteCommentD extends TestBase {

	public static String deleteCommentRsr(String bugId, String commentId) {
		return "/rest/api/2/issue/" + bugId + "/comment/" + commentId;
	}

	public void deleteComment(String token, String bugId, String commentId) {

		given().header("Cookie", token).when().delete(deleteCommentRsr(bugId, commentId)).then().statusCode(204);

	}

}
