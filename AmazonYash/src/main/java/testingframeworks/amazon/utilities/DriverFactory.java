package testingframeworks.amazon.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

	private DriverFactory() {

	}

	static WebDriver singleDriver;
	static String selectedBrowser = PageUtilities.SELECTED_BROWSER;

	public static WebDriver getDriver() {
		if (closedTheDriver() || (selectedBrowser!=PageUtilities.SELECTED_BROWSER)) {
			selectedBrowser = PageUtilities.SELECTED_BROWSER;
			return initDriver();
		} else {
			if (singleDriver == null) {
				singleDriver = initDriver();
			}
			return singleDriver;
		}
	}

	public static WebDriver initDriver() {
		WebDriver driver;
		
		if (selectedBrowser.equalsIgnoreCase("headless")) {
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
		} else {
			String pathToDriver = PropertiesFileInteraction.getProperty("path."+selectedBrowser,
					PageUtilities.UTILITY_CONFIG_FILE);
			String selectedDriver = PropertiesFileInteraction.getProperty("property."+selectedBrowser, PageUtilities.UTILITY_CONFIG_FILE);
			
			System.setProperty(selectedDriver, pathToDriver);
			if (selectedDriver.contains("chrome"))
				driver = new ChromeDriver();
			else
				driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		int waitTime = Integer.parseInt(PropertiesFileInteraction.getProperty("wait.implicit", PageUtilities.UTILITY_CONFIG_FILE));
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		String url = PropertiesFileInteraction.getProperty("test.url", PageUtilities.UTILITY_CONFIG_FILE);
		driver.get(url);
		return driver;
	}

	public static void closeDriver(WebDriver driver) {
		if (closedTheDriver())
			driver.close();
	}

	public static boolean closedTheDriver() {
		return (PropertiesFileInteraction.getProperty("browser.new.instance", PageUtilities.TEST_CONFIG_FILE)
				.equalsIgnoreCase("true")
				|| PropertiesFileInteraction.getProperty("browsers.executiont.type", PageUtilities.TEST_CONFIG_FILE)
						.equalsIgnoreCase("parallel"));
	}

}
