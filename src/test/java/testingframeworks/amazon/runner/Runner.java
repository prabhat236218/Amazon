package testingframeworks.amazon.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import testingframeworks.amazon.utilities.PageUtilities;
import testingframeworks.amazon.utilities.PropertiesFileInteraction;

public class Runner {

	public static void main(String args[]) {
		TestNG testNG = new TestNG();
		configure(testNG);
		String browsers = PropertiesFileInteraction.getProperty("browsers.list", PageUtilities.TEST_CONFIG_FILE);
		List<String> listOfBrowsers = Arrays.asList(browsers.split("\\s*,\\s*"));
		System.out.println(listOfBrowsers);
		for (String browser : listOfBrowsers) {
			try {
				PageUtilities.SELECTED_BROWSER = browser;
				testNG.run();
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static void configure(TestNG testNG) {
		String executionType = PropertiesFileInteraction.getProperty("browsers.executiont.type",
				PageUtilities.TEST_CONFIG_FILE);
		XmlSuite suite = new XmlSuite();
		suite.setName("Suite");
		if (executionType.equalsIgnoreCase("parallel")) {
			suite.setParallel(XmlSuite.ParallelMode.TESTS);
			suite.setThreadCount(5);
		}
		
		setTest(suite, "AddToCartTest", "testingframeworks.amazon.testcases.AddToCartTest");
		setTest(suite, "CheckIfUserIsPrime", "testingframeworks.amazon.testcases.CheckIfUserIsPrime");
		setTest(suite, "LastOrderTest", "testingframeworks.amazon.testcases.LastOrderTest");
		setTest(suite, "SearchTest", "testingframeworks.amazon.testcases.SearchTest");
		setTest(suite, "TodaysDealTest", "testingframeworks.amazon.testcases.TodaysDealTest");
		
		
		List<XmlSuite> suites = new ArrayList<>();
		suites.add(suite);
		
		testNG.setXmlSuites(suites);
	}
	
	public static void setTest(XmlSuite suite, String testName, String className) {
		XmlTest test = new XmlTest(suite);
		test.setName(testName);
		List<XmlClass> todaysDealClasses = new ArrayList<>();
		todaysDealClasses.add(new XmlClass(className));
		test.setClasses(todaysDealClasses);
	}
	

}
