package testingframeworks.amazon.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProductPage {
	WebDriver driver;
	
	@FindBy(how = How.XPATH, using = "//input[@id='add-to-cart-button']")
	WebElement addToCartButton;
	
	@FindBy(how = How.XPATH, using  = "//a[@id='hlb-view-cart-announce']")
	WebElement cartButton;
	
	
	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean addProductToCart() {
		try { 
			addToCartButton.click();
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public void goToCart() {
		cartButton.click();
	}
}
