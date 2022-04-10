package com.amazon.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.amazon.qa.base.TestBase;

public class HomePage extends TestBase {

	
	// Search Amazon Product
	@FindBy(xpath = "//*[@id='twotabsearchtextbox']")
	WebElement searchBox;
	
	@FindBy(xpath = "//*[@id='nav-search-submit-button']")
	WebElement searchButton;
	
	@FindBy(xpath = "//*[contains(@id,'productTitle')]")
	WebElement productPageTitle;
	
	@FindBy(xpath = "//*[contains(@id,'add-to-cart-button')]")
	WebElement addToCartButton;
	
	@FindBy(xpath = "//div[contains(@id,'nav-cart-count')]")
	WebElement viewCartButton;
	
	@FindBy(xpath = "//*[@name='quantityBox']")
	WebElement qtyBox;
	
	@FindBy(xpath = "//div[@id='proceed-to-checkout-desktop-container']//span[@id='sc-subtotal-amount-buybox']")
	WebElement totalAmount;
	

	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	// Search Amazon Product
	
	public void searchProduct(String product) {
		searchBox.click();
		searchBox.sendKeys(product);
		searchButton.click();
	}
	
	public boolean verifyTheList(String keyword) {
		  List<WebElement> searchResult = driver.findElements(By.xpath("//*[@class='a-section' or @class='sg-col-inner']//a/span[contains(@class,'a-text-normal')]"));
		  boolean flag = true; 
		  for(int i =0;i<searchResult.size();i++)
		  {
			  if (!(searchResult.get(i).getText().toUpperCase().contains(keyword.toUpperCase())||searchResult.size()>1)) {
				  flag = false;
			  }
		  }
		  return flag;
	}
	
	public void navigateToProductPage(int productIndex) {
		driver.findElement(By.xpath("(//*[@class='a-section' or @class='sg-col-inner']//a/span[contains(@class,'a-text-normal')])["+productIndex+"]")).click();
	}
	
	public void switchTab(int index) {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(index));
	}
	
	public boolean validateProductPageLoaded() {
		return productPageTitle.isDisplayed();
	}
	
	public String getProductName() {
		String mystring =  productPageTitle.getText();
		String arr[] = mystring.split(" ", 2);
		String firstWord = arr[0];
		return firstWord;
	}
	
	public void clickAddToCardButton() {
		addToCartButton.click();
	}
	
	public void navigateToShoppingCart() {
		viewCartButton.click();
	}
	
	public String getShoppingCartProductName(int index) {
		return driver.findElement(By.xpath("(//span[contains(@class,'a-truncate')])["+index+"]")).getText();
	}
	
	public void changeQtyProduct(int prodIndex, String qty) {
		Select quantity = new Select(driver.findElement(By.xpath("(//div[contains(@class,'sc-list-item-content')]//select[contains(@name,'quantity')])["+prodIndex+"]")));
		if(qty.contains("10")) {
		quantity.selectByVisibleText("10+");
		} else quantity.selectByVisibleText(qty);
		if(qty.contains("10")||Integer.parseInt(qty)>10) {
			qtyBox.sendKeys(qty);
			driver.findElement(By.xpath("(//a[contains(text(),'Update')])["+prodIndex+"]")).click();
		}
	}
	
	public double getTotalAmount() {
		String amount = totalAmount.getText();
		return Double.parseDouble(amount.trim());
	}
	
	public void removeItemFromShoppingCart(int prodIndex){
		driver.findElement(By.xpath("(//a[contains(text(),'Delete')])["+prodIndex+"]")).click();
	}
	
	public void removeItemFromShoppingCart(String product){
		driver.findElement(By.xpath("//span[contains(text(),'"+product+"') and contains(@class,'a-truncate-full')]//ancestor::div[contains(@class,'a-column')]//input[@data-action='delete']")).click();
	}
	
	public boolean validateProductRemoved(String product) {
		
		return driver.findElement(By.xpath("//span[contains(text(),'"+product+"') and contains(@class,'a-truncate-full')]//ancestor::div[contains(@class,'a-column')]//input[@data-action='delete']")).isDisplayed();
		 
	}
	

}
