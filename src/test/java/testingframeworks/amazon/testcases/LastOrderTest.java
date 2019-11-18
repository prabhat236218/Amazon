package testingframeworks.amazon.testcases;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testingframeworks.amazon.pages.HomePage;
import testingframeworks.amazon.pages.SignInPage;
import testingframeworks.amazon.pages.YourOrderPage;
import testingframeworks.amazon.utilities.DriverFactory;
import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;
import testingframeworks.amazon.utilities.Report;

@Listeners(testingframeworks.amazon.utilities.TestListeners.class)
public class LastOrderTest {

	public WebDriver driver;
	int fiveSecond = Integer.parseInt(PropertiesFileInteraction.getProperty("wait.fiveSecond", PageUtilities.UTILITY_CONFIG_FILE));

	@BeforeTest
	public void setUp(ITestContext context) {
		driver = DriverFactory.getDriver();
		context.setAttribute("webDriver", this.driver);
	}

	@Test
	public void getLastOrder() {
		HomePage forSignIn = PageFactory.initElements(driver, HomePage.class);
		if (forSignIn.checkIfNotSignedIn()) {
			forSignIn.goToSignInPage();
			SignInPage forSignInPage = PageFactory.initElements(driver, SignInPage.class);
			String email = PropertiesFileInteraction.getProperty("user.correct.email", PageUtilities.TEST_CONFIG_FILE);
			String password = PropertiesFileInteraction.getProperty("user.correct.password",
					PageUtilities.TEST_CONFIG_FILE);
			try {
				forSignInPage.enter(email, "email");
			} catch (NoSuchElementException e) {
				Log.inWarnAdd("Sign in did not work in one click");
				Report.getTest().log(Status.WARNING, "Sign in did not work in one click");
				forSignIn.goToSignInPageAlternate();
				forSignInPage = PageFactory.initElements(driver, SignInPage.class);
			}
			PageUtilities.addWaitFor(fiveSecond);
			forSignInPage.enter(password, "password");
		}
		forSignIn.goToYourOrder();
		YourOrderPage toGetOrder = PageFactory.initElements(driver, YourOrderPage.class);
		Report.getTest().log(Status.INFO, "Last order: " + toGetOrder.getNameOfLastOrder());
		Log.inInfoAdd("Last order: " + toGetOrder.getNameOfLastOrder());
	}

	@AfterTest
	public void closeDriver() {
		DriverFactory.closeDriver(driver);
	}
}
