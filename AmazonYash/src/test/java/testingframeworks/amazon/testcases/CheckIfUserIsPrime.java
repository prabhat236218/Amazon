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
import testingframeworks.amazon.utilities.CustomAssertion;
import testingframeworks.amazon.utilities.DriverFactory;
import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;
import testingframeworks.amazon.utilities.Report;


@Listeners(testingframeworks.amazon.utilities.TestListeners.class)
public class CheckIfUserIsPrime {
	
	public WebDriver driver;
	int oneSecond = Integer.parseInt(PropertiesFileInteraction.getProperty("wait.oneSecond", PageUtilities.UTILITY_CONFIG_FILE));
	int threeSecond = Integer.parseInt(PropertiesFileInteraction.getProperty("wait.threeSecond", PageUtilities.UTILITY_CONFIG_FILE));
	
	@BeforeTest
	public void setUp(ITestContext context){
		driver = DriverFactory.getDriver();
        context.setAttribute("webDriver", this.driver);
    }
	
	@Test
	public void checkIsUserPrime() {
		HomePage forPrimeCheck = PageFactory.initElements(driver, HomePage.class);
		if(forPrimeCheck.checkIfNotSignedIn()) {
			forPrimeCheck.goToSignInPage();
			PageUtilities.addWaitFor(oneSecond);
			SignInPage forSignInPage = PageFactory.initElements(driver, SignInPage.class);
			PageUtilities.addWaitFor(oneSecond);
			String email = PropertiesFileInteraction.getProperty("user.correct.email", PageUtilities.TEST_CONFIG_FILE);
			String password = PropertiesFileInteraction.getProperty("user.correct.password", PageUtilities.TEST_CONFIG_FILE);
			try {
				PageUtilities.addWaitFor(threeSecond);
				forSignInPage.enter(email, "email");
			} catch (NoSuchElementException e) {
				Log.inWarnAdd("Sign in did not work in one click");
				Report.getTest().log(Status.WARNING, "Sign in did not work in one click");
				forPrimeCheck.goToSignInPageAlternate();
				forSignInPage = PageFactory.initElements(driver, SignInPage.class);
			}
			PageUtilities.addWaitFor(threeSecond);
			forSignInPage.enter(password, "password");
			PageUtilities.addWaitFor(oneSecond);
		}
		CustomAssertion customAssertion = new CustomAssertion("CheckIfUserIsPrime");
		customAssertion.assertTrue(forPrimeCheck.isCustomerAPrimeMember());
	}
	
	@AfterTest
	public void closeDriver() {
		DriverFactory.closeDriver(driver);
	}
}
