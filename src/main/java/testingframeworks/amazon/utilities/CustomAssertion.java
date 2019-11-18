package testingframeworks.amazon.utilities;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class CustomAssertion {

	SoftAssert softAssert;
	
	public CustomAssertion(String testName) {

	}
	
	public void assertEquals(Object actual, Object expected) {
		Reporter.log("ACTUAL VALUE: "+actual.toString());
		Reporter.log("EXPECTED VALUE: "+expected.toString());
		Report.getTest().log(Status.INFO,"ACTUAL VALUE: "+actual.toString());
		Report.getTest().log(Status.INFO,"EXPECTED VALUE: "+expected.toString());
		Assert.assertEquals(actual, expected);
	}
	
	public void assertTrue(boolean actual) {
		Reporter.log("ACUTAL VAUE: "+actual);
		Reporter.log("EXPECTED VALUE: "+true);
		Report.getTest().log(Status.INFO,"ACTUAL VALUE: "+actual);
		Report.getTest().log(Status.INFO,"EXPECTED VALUE: "+true);
		Assert.assertTrue(actual);
	}
	
	public void softAssertNotEquals(Object actual, Object expected) {
		Reporter.log("ACTUAL VALUE: "+actual.toString());
		Reporter.log("NOT EXPECTED VALUE: "+expected.toString());
		Report.getTest().log(Status.INFO,"ACTUAL VALUE: "+actual.toString());
		Report.getTest().log(Status.INFO,"NOT EXPECTED VALUE: "+expected.toString());
		softAssert.assertNotEquals(actual, expected);
	}
	
	public void softAssertEquals(Object actual, Object expected) {
		Reporter.log("ACTUAL VALUE: "+actual.toString());
		Reporter.log("EXPECTED VALUE: "+expected.toString());
		Report.getTest().log(Status.INFO,"ACTUAL VALUE: "+actual.toString());
		Report.getTest().log(Status.INFO,"EXPECTED VALUE: "+expected.toString());
		softAssert.assertEquals(actual, expected);
	}
	
	public void softAssertTrue(boolean value) {
		Reporter.log("ACTUAL VALUE: "+value);
		Reporter.log("EXPECTED VALUE: "+true);
		Report.getTest().log(Status.INFO,"ACTUAL VALUE: "+value);
		Report.getTest().log(Status.INFO,"EXPECTED VALUE: "+true);
		softAssert.assertTrue(value);
	}
	
	public void fail(String message) {
		Reporter.log("FAILED AT: "+message);
		Report.getTest().log(Status.INFO,"FAILED AT: "+message);
		Assert.fail("FAILED AT: "+message);
	}
	
	public void setSoftAssert(SoftAssert softAssert) {
		this.softAssert = softAssert;
	}
 
}
