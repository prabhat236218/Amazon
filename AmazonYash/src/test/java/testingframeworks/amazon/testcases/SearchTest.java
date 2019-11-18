package testingframeworks.amazon.testcases;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import testingframeworks.amazon.pages.HomePage;
import testingframeworks.amazon.pages.SearchResultPage;
import testingframeworks.amazon.utilities.CustomAssertion;
import testingframeworks.amazon.utilities.DriverFactory;
import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;
import testingframeworks.amazon.utilities.Report;

@Listeners(testingframeworks.amazon.utilities.TestListeners.class)
public class SearchTest {

	public WebDriver driver;
	CustomAssertion customAssertion = new CustomAssertion("SearchTest");
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	public void setUp(ITestContext context) {
		driver = DriverFactory.getDriver();
		context.setAttribute("webDriver", this.driver);
		customAssertion.setSoftAssert(softAssert);
	}

	@Test
	public void searchForAProduct() {
		String products = PropertiesFileInteraction.getProperty("products.search", PageUtilities.TEST_CONFIG_FILE);
		List<String> listOfProducts = Arrays.asList(products.split("\\s*,\\s*"));
		for(String product: listOfProducts) {
			try {
				search(product);
			} catch(Exception e) {
				continue;
			}
		}
	}

	public void search(String product) {
		HomePage forSearch = PageFactory.initElements(driver, HomePage.class);
		forSearch.searchFor(product);
		SearchResultPage forSearchResults = PageFactory.initElements(driver, SearchResultPage.class);
		int totalPages = forSearchResults.getNumberOfPages();
		Report.getTest().log(Status.INFO, totalPages + " number of pages for " + product + " search");
		Log.inInfoAdd(totalPages + " number of pages for " + product + " search");
		Map<String, String> productsAndPriceList = forSearchResults.getNameAndPriceOfProductsOnPage();
		for (Map.Entry<String, String> productAndPrice : productsAndPriceList.entrySet()) {
			Report.getTest().log(Status.INFO,
					"Product: " + productAndPrice.getKey() + " Price: " + productAndPrice.getValue());
			Log.inInfoAdd("Product: " + productAndPrice.getKey() + " Price: " + productAndPrice.getValue());
		}
		customAssertion.softAssertTrue(totalPages > 1);
	}
	
	@AfterTest
	public void closeDriver() {
		DriverFactory.closeDriver(driver);
	}
}
