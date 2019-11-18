package testingframeworks.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class YourOrderPage {
	WebDriver driver;
	
	@FindBy(how = How.XPATH, using="//div[@class='a-row']/a[@class='a-link-normal']")
	WebElement nameOfLastOrder;
	
	public YourOrderPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public String getNameOfLastOrder() {
		return nameOfLastOrder.getText();
	}
}
