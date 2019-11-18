package testingframeworks.amazon.utilities;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

	Report report = Report.getInstance();
	ExtentTest logger;

	public void onTestStart(ITestResult result) {
		logger = report.createExtent(result.getName()+" "+PageUtilities.SELECTED_BROWSER);
		Report.setTest(logger);
		Report.getTest().log(Status.INFO, "TEST STARTED:");
		Report.getTest().log(Status.INFO, "BROWSER: "+PageUtilities.SELECTED_BROWSER);
		Log.inInfoAdd("TEST STARTED:" + result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		Report.getTest().log(Status.INFO, "TEST SUCCESSFULL:");
		Log.inInfoAdd("TEST SUCCESSFULL:" + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		String testName = result.getName();
		Report.getTest().log(Status.FAIL, result.getThrowable().getMessage());
		Report.getTest().log(Status.FAIL, "TEST FAILED");
		Log.inFatalAdd("TEST FAILED:" + testName);
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("webDriver");
		PageUtilities.getScreenshotOfCurrentScreenAndSaveWith(testName, driver, Report.getTest());
	}

	public void onTestSkipped(ITestResult result) {
		logger = report.createExtent(result.getName());
		Report.setTest(logger);
		Report.getTest().log(Status.WARNING, "TEST SKIPPED");
		Report.getTest().log(Status.INFO, "BROWSER: "+PageUtilities.SELECTED_BROWSER);
		Log.inWarnAdd("TEST SKIPPED:" + result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Report.getTest().log(Status.DEBUG, "TEST FAILED WITH SUCCESS PERCENTAGE:");
		Log.inDebugAdd("TEST FAILED WITH SUCCESS PERCENTAGE:" + result.getName());
	}

	public void onStart(ITestContext context) {
		report = Report.getInstance();
		Log.inInfoAdd("LISTENER STARTED");
	}

	public void onFinish(ITestContext context) {
		report.finishReport();
		Log.inInfoAdd("LISTENER STOPPED");
	}

}
