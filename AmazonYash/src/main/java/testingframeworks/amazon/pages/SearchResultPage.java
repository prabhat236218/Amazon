package testingframeworks.amazon.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;

public class SearchResultPage {

	WebDriver driver;
	
	@FindBys({
		@FindBy(how = How.XPATH, using = "//div[@class='a-section a-spacing-medium']")
	})
	List<WebElement> listOfProducts;
	
	@FindBy(how = How.XPATH, using = "//ul[@class='a-pagination']//li[@class='a-disabled'][2]")
	WebElement numberOfPages;
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getNumberOfPages() {
		try {
			return Integer.parseInt(numberOfPages.getText());
		} catch(NoSuchElementException e) {
			return 1;
		}
	}
	
	public Map<String,String> getNameAndPriceOfProductsOnPage() {
		Map<String, String> listOfPriceAndProducts = new HashMap<>();
		String productName;
		String productPrice;
		for(WebElement product: listOfProducts) {
			try {
				productName = product.findElement(By.xpath("")).getText();
				productPrice = product.findElement(By.xpath("./span[@class='a-price-whole']")).getText();
			} catch(NoSuchElementException e) {
				continue;
			}
			listOfPriceAndProducts.put(productName, productPrice);
		}
		return listOfPriceAndProducts;
	}
	
	public void clickOnFirstProduct() {
		listOfProducts.get(0).findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']")).click();
	}

}
