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

import testingframeworks.amazon.pages.DealsPage;
import testingframeworks.amazon.pages.HomePage;
import testingframeworks.amazon.utilities.CustomAssertion;
import testingframeworks.amazon.utilities.DriverFactory;
import testingframeworks.amazon.utilities.Log;
import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;
import testingframeworks.amazon.utilities.Report;

@Listeners(testingframeworks.amazon.utilities.TestListeners.class)
public class TodaysDealTest {

	public WebDriver driver;
	CustomAssertion customAassertion = new CustomAssertion("TodaysDealTest");
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	public void setUp(ITestContext context) {
		driver = DriverFactory.getDriver();
		context.setAttribute("webDriver", this.driver);
		customAassertion.setSoftAssert(softAssert);
	}

	@Test
	public void getListOfTodaysDeals() {
		String productFilters = PropertiesFileInteraction.getProperty("products.type.filter",
				PageUtilities.TEST_CONFIG_FILE);
		System.out.println(productFilters);
		List<String> listOfProductFilters = Arrays.asList(productFilters.split("\\s*,\\s*"));
		if (listOfProductFilters.size() == 0) {
			assertNumberOfDeals("");
		} else {
			for (String productFilter : listOfProductFilters) {
				try {
					assertNumberOfDeals(productFilter);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
	}

	public void assertNumberOfDeals(String productFilter) {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.goToTodaysDeals();
		DealsPage dealPage = PageFactory.initElements(driver, DealsPage.class);
		if (!productFilter.equals(""))
			dealPage = addFilter(productFilter, dealPage);
		Map<String, String> listOfDeals = dealPage.getListOfDealsOnPage();
		for (Map.Entry<String, String> deal : listOfDeals.entrySet()) {
			Report.getTest().log(Status.INFO, deal.getKey() + " is of " + deal.getValue());
			Log.inInfoAdd(deal.getKey() + " is of " + deal.getValue());
		}
		customAassertion.softAssertTrue(dealPage.getNumberOfPages() > 0);
	}

	public DealsPage addFilter(String productType, DealsPage dealPage) {
		dealPage.addFilterFor(productType);
		return PageFactory.initElements(driver, DealsPage.class);
	}

	@AfterTest
	public void closeDriver() {
		DriverFactory.closeDriver(driver);
	}
}
