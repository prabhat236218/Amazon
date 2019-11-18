package testingframeworks.amazon.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import testingframeworks.amazon.pages.CartPage;
import testingframeworks.amazon.pages.HomePage;
import testingframeworks.amazon.pages.ProductPage;
import testingframeworks.amazon.pages.SearchResultPage;
import testingframeworks.amazon.utilities.CustomAssertion;
import testingframeworks.amazon.utilities.DriverFactory;
import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;
import testingframeworks.amazon.utilities.Report;

@Listeners(testingframeworks.amazon.utilities.TestListeners.class)
public class AddToCartTest {

	public WebDriver driver;
	int oneSecond = Integer.parseInt(PropertiesFileInteraction.getProperty("wait.oneSecond", PageUtilities.UTILITY_CONFIG_FILE));

	@BeforeTest
	public void setUp(ITestContext context) {
		driver = DriverFactory.getDriver();
		context.setAttribute("webDriver", this.driver);
	}
	
	@Test
	public void addToCartAndCartTotalCheck() {
		String products = PropertiesFileInteraction.getProperty("products.toadd",PageUtilities.TEST_CONFIG_FILE);
		List<String> listOfProducts = Arrays.asList(products.split("\\s*,\\s*"));
		for(String product: listOfProducts) {
			try {
				addToCart(product);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		checkCartTotalValueAndTotalItems(listOfProducts.size());
	}

	
	public void checkCartTotalValueAndTotalItems(int numberOfProductsToAdd) {
		CustomAssertion customAssertion = new CustomAssertion("AddToCartTest");
		SoftAssert softAssert = new SoftAssert();
		customAssertion.setSoftAssert(softAssert);
		ProductPage goToCart = PageFactory.initElements(driver, ProductPage.class);
		goToCart.goToCart();
		CartPage cartPage = PageFactory.initElements(driver, CartPage.class);
		int totalItemsInCart = cartPage.getTotalItemsInTheCart();
		if(numberOfProductsToAdd != totalItemsInCart) {
			Report.getTest().log(Status.WARNING, "Not all items are added in cart");
			Log.inWarnAdd("Not all items are added in cart");
		}
		customAssertion.softAssertEquals(numberOfProductsToAdd, numberOfProductsToAdd);
		int totalOfAllProducts = getTotalOfAllProducts(cartPage);
		int subtotalOfCart = cartPage.getCartSubTotal();
		customAssertion.softAssertEquals(totalOfAllProducts, subtotalOfCart);
	}
	
	public int getTotalOfAllProducts(CartPage cartPage) {
		int total = 0;
		List<String> listOfPrices = cartPage.getListOfPriceOfProductsIntCart();
		for(String price: listOfPrices) {
			total += Integer.parseInt(price);
		}
		return total;
	}
	
	public void addToCart(String product) {
		HomePage forSearch = PageFactory.initElements(driver, HomePage.class);
		PageUtilities.addWaitFor(oneSecond);
		forSearch.searchFor(product);
		PageUtilities.addWaitFor(oneSecond);
		SearchResultPage forSearchResults = PageFactory.initElements(driver, SearchResultPage.class);
		forSearchResults.clickOnFirstProduct();
		PageUtilities.addWaitFor(oneSecond);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(tabs.get(1));
		ProductPage forProductPage = PageFactory.initElements(driver, ProductPage.class);
		if(forProductPage.addProductToCart()) {
			Report.getTest().log(Status.INFO, product+" added to cart");
			Log.inInfoAdd(product+" added to cart");
		} else {
			Report.getTest().log(Status.WARNING, product+" cannot be added to cart");
			Log.inWarnAdd(product+" cannot be added to cart");
		}
	}

	@AfterTest
	public void closeDriver() {
		DriverFactory.closeDriver(driver);
	}
}
