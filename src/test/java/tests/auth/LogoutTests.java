package tests.auth;

import org.testng.annotations.*;

import base.TestBase;
import resources.LogoutD;

public class LogoutTests extends TestBase {

	private LogoutD lg;
	private String token;

	@BeforeTest
	private void setUp() {
		lg = new LogoutD();
	}

	@BeforeMethod
	private void logIn() {
		token = getToken();
	}

	@Test
	private void logoutTest() {
		lg.logout(token, 204);
	}

	@Test
	private void logoutWrongTokenTest() {
		lg.logout(token + 1, 401);
	}

	@Test
	private void logoutWithoutAuthTest() {
		lg.logout(token);
		lg.logout(token, 401);
	}

	@AfterMethod
	private void tearDown() {
		lg.logout(token);
	}

}
