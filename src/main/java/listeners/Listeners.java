package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.TestBase;

public class Listeners extends TestBase implements ITestListener{
	

	public void onTestStart(ITestResult result) {
		log.info(result.getName() + " test started");
		
	}

	public void onTestSuccess(ITestResult result) {
		log.info(result.getName() + " test succeeded");
		
	}

	public void onTestFailure(ITestResult result) {
		log.error(result.getName() + " test failed");
		
	}

	public void onTestSkipped(ITestResult result) {
		log.info(result.getName() + " test skipped");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
