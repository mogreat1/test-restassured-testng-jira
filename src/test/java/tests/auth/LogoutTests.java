package tests.auth;

import org.testng.annotations.*;

import base.TestBase;
import resources.LogoutD;

public class LogoutTests extends TestBase {

	LogoutD lg;
	String token;

	@BeforeTest
	public void setUp() {
		lg = new LogoutD();
	}

	@BeforeMethod
	public void logIn() {
		token = getToken();
	}

	@Test
	public void logoutTest() {
		lg.logout(token, 204);
	}

	@Test
	public void logoutWrongTokenTest() {
		lg.logout(token + 1, 401);
	}

	@Test
	public void logoutWithoutAuthTest() {
		lg.logout(token);
		lg.logout(token, 401);
	}

	@AfterMethod
	public void tearDown() {
		lg.logout(token);
	}

}
