package testingframeworks.amazon.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.Status;

import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.Report;


public class HomePage {

	WebDriver driver;

	@FindBy(how = How.XPATH, using = "//a[@class='nav-sprite nav-logo-tagline']")
	WebElement primeLogo;

	@FindBy(how = How.XPATH, using = "//input[@id='twotabsearchtextbox']")
	WebElement searchBar;

	@FindBy(how = How.XPATH, using = "//a[@href='/gp/sva/dashboard?ref_=nav_apay']")
	WebElement amazonPayButton;

	@FindBy(how = How.XPATH, using = "//a[@id='nav-link-accountList']")
	WebElement clickForSingIn;

	@FindBy(how = How.XPATH, using = "//a[@id='nav-orders']")
	WebElement yourOrders;

	@FindBy(how = How.XPATH, using = "//a[@id='nav-link-accountList']/span")
	WebElement userNameText;

	@FindBy(how = How.XPATH, using = "//a[contains(text(), 'Deals')]")
	WebElement todaysDealButton;

	@FindBy(how = How.XPATH, using = "//a[@id='nav-hamburger-menu']")
	WebElement hamburgerMenuButton;

	@FindBy(how = How.XPATH, using = "//div[@id='hmenu-customer-profile']")
	WebElement hamburgrMenuProfile;
	
	@FindBy(how = How.XPATH, using = "//a[@id='nav-link-accountList']")
	WebElement alternateSignIn;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isCustomerAPrimeMember() {
		try {
			return primeLogo.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void goToYourOrder() {
		yourOrders.click();
	}

	public void searchFor(String product) {
		searchBar.sendKeys(product);
		searchBar.sendKeys(Keys.ENTER);
		searchBar.clear();
	}

	public void goToAmazonPayPage() {
		amazonPayButton.click();
	}

	public void goToSignInPage() {
		try {
			hamburgerMenuButton.click();
			hamburgrMenuProfile.click();
		} catch (NoSuchElementException e) {
			Log.inWarnAdd("Using alternate sign in");
			Report.getTest().log(Status.WARNING, "Using alternate sign in");
			goToSignInPageAlternate();
		}
	}
	
	public void goToSignInPageAlternate() {
		alternateSignIn.click();
	}

	public void goToTodaysDeals() {
		todaysDealButton.click();
	}

	public boolean checkIfNotSignedIn() {
		return userNameText.getText().contains("Sign in");
	}
}
