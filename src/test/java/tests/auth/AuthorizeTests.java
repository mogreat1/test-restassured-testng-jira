package tests.auth;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import resources.AuthorizePost;

public class AuthorizeTests extends TestBase {
	private AuthorizePost ap;

	@BeforeMethod
	private void setUp() {
		ap = new AuthorizePost();
	}

	@Test
	private void authorizeTest() {
		js = ap.authorizePost(prop.getProperty("username"), prop.getProperty("password"), 200);
		Assert.assertEquals(js.get("session.name"), "JSESSIONID");

	}

	@Test
	private void invalidUsername() {
		js = ap.authorizePost("something", prop.getProperty("password"), 401);
		Assert.assertEquals(js.get("errorMessages[0]"), "Login failed");

	}

	@Test
	private void invalidPassword() {
		js = ap.authorizePost(prop.getProperty("username"), "password", 401);
		Assert.assertEquals(js.get("errorMessages[0]"), "Login failed");

	}

	@Test(dataProvider = "loginData")
	private void invalidMultyAuthorizeTest(String username, String password) {
		js = ap.authorizePost(username, password, 401);
		Assert.assertEquals(js.get("errorMessages[0]"), "Login failed");
	}

	@DataProvider(name = "loginData")
	private Object[][] getData() {
		return new Object[][] { { "one", "123" }, { "two", "321" }, { "three", "456" } };

	}

}
