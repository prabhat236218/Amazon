package testingframeworks.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SignInPage {
	
	WebDriver driver;
	
	@FindBy(how = How.XPATH, using = "//input[@id='ap_email']")
	WebElement emailInputBox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='ap_password']")
	WebElement passwordInputBox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='continue']")
	WebElement continueButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='signInSubmit']")
	WebElement singInButton;
	
	
	public void enter(String data, String inField) {
		switch(inField) {
		case "email" : emailInputBox.sendKeys(data);
						continueButton.click();
						break;
		case "password" : passwordInputBox.sendKeys(data);
						singInButton.click();
						break;
		}
	}
	
	
	
	

}
