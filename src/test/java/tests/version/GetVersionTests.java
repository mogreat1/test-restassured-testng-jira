package tests.version;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.TestBase;
import resources.AddVersionPost;
import resources.DeleteVersionD;
import resources.GetVersionG;

public class GetVersionTests extends TestBase {

	private String token;
	private String versionId;
	private String name;

	AddVersionPost av;
	GetVersionG gv;
	DeleteVersionD dv;

	@BeforeTest
	public void setUp() {
		av = new AddVersionPost();
		gv = new GetVersionG();
		dv = new DeleteVersionD();

		token = getToken();

		name = "nameSVD";
		versionId = av.addVersion(token, name);

	}

	@Test
	public void getVersionTest() {

		js = gv.getVetsion(token, versionId, 200);
		SoftAssert sa = new SoftAssert();

		sa.assertEquals(js.get("projectId"), prop.getProperty("projectId"));
		sa.assertEquals(js.get("name"), name);
		sa.assertEquals(js.get("id"), versionId);
		sa.assertAll();

	}

	@AfterTest
	public void deleteVersion() {
		dv.deleteVersion(token, versionId);
	}

}
