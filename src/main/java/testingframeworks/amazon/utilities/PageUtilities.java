package testingframeworks.amazon.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;

public class PageUtilities {
	
	public static final String TEST_CONFIG_FILE = "test.properties";
	public static final String UTILITY_CONFIG_FILE = "utility.properties";
	public static String SELECTED_BROWSER = "gecko";
	
	private PageUtilities() {
		
	}

	public static String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public static void addWaitFor(long seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			Log.inFatalAdd("EXCEPTION OCCURED WHILE ADDING WAIT");
			Thread.currentThread().interrupt();
		}
	}
	
	public static Timestamp getCurrentTimeStamp() {
		Date date = new Date();
		long time  = date.getTime();
		return new Timestamp(time);
	}
	
	public static boolean getScreenshotOfCurrentScreenAndSaveWith(String name, WebDriver driver, ExtentTest logger) {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String basePath = PropertiesFileInteraction.getProperty("path.base.screenshot", UTILITY_CONFIG_FILE);
		try {
			File screenshot = new File(basePath+name+".png");
			FileHandler.copy(screenshotFile, screenshot);
			String path = "<img src=\""+screenshot.toString()+"\"/>";
			Report report = Report.getInstance();
			report.addScreenCapture(screenshot.toString(), logger);
			Reporter.log(path);
			return true;
		} catch (IOException e) {
			Log.inFatalAdd("EXCEPTION OCCURED WHILE TAKING SCREENSHOT IN TEST: "+name+" "+e.getMessage());
			return false;
		}
	}
}
