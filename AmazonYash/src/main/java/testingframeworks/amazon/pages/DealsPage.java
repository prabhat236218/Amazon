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

public class DealsPage {
	WebDriver driver; 
	
	@FindBys ({
		@FindBy(how = How.XPATH, using="//div[@id='widgetFilters']//div//div[@class='a-checkbox checkbox a-spacing-micro']")
	})
	List<WebElement> productTypeListFilter; 
	
	@FindBy(how = How.XPATH, using = "//div[@id='pagination-next-17756310700879196']//li[@class='a-disabled'][3]")
	WebElement numberOfPages;
	
	@FindBys({
		@FindBy(how = How.XPATH, using = "//div[@class='a-fixed-left-grid-col rightCol']//div[@class='a-section dealContainer']")
	})
	List<WebElement> listOfDeals;
	
	
	public DealsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void addFilterFor(String productType) {
		for(WebElement productTypeFilter: productTypeListFilter) {
			String labelText = productTypeFilter.findElement(By.xpath(".//span")).getText();
			if(labelText.equalsIgnoreCase(productType)) {
				productTypeFilter.click();
				break;
			}
		}
	}
	
	public Map<String, String> getListOfDealsOnPage() {
		Map<String, String> productAndPriceList = new HashMap<>();
		String productName; 
		String productPrice;
		System.out.println(listOfDeals);
		for(WebElement deal: listOfDeals) {
			productName = deal.findElement(By.xpath(".//a[@id='dealTitle']//span")).getText();
			productPrice = deal.findElement(By.xpath(".//span[@class='a-size-medium inlineBlock unitLineHeight dealPriceText']")).getText();
			productAndPriceList.put(productName, productPrice);
		}
		return productAndPriceList;
	}
	
	public int getNumberOfPages() {
		try {
			return Integer.parseInt(numberOfPages.getText());
		} catch (NoSuchElementException e) {
			return 1;
		}
	}

}
