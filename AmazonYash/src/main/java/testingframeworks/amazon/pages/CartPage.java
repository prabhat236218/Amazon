package testingframeworks.amazon.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;

public class CartPage {
	
	WebDriver driver;
	
	@FindBys({
		@FindBy(how = How.XPATH, using = "//div[@id='sc-active-cart']//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-product-price sc-price-sign a-text-bold']")
	})
	List<WebElement> listOfProductPricesInTheCart;
	
	@FindBy(how = How.XPATH, using = "//span[@id='sc-subtotal-amount-activecart']//span")
	WebElement subTotalOfTheCart;
	
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
	}

	public List<String> getListOfPriceOfProductsIntCart() {
		List<String> listOfPrices = new ArrayList<>();
		for(WebElement price: listOfProductPricesInTheCart) {
			listOfPrices.add(price.getText().replaceAll("[^0-9]", ""));
		}
		return listOfPrices;
	}
	 
	public int getTotalItemsInTheCart() {
		return listOfProductPricesInTheCart.size();
	}
	
	public int getCartSubTotal() {
		return Integer.parseInt(subTotalOfTheCart.getText().replaceAll("[^0-9]", ""));
	}
	
}
