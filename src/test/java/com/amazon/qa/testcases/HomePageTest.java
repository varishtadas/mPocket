package com.amazon.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amazon.qa.base.TestBase;
import com.amazon.qa.pages.HomePage;
import com.amazon.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	HomePage homePage;
	TestUtil testUtil;

	public HomePageTest() {
		super();
	}
/**
	1. Search for a product. Verify the list.
	2. Add a product to the cart. Verify that the added product is in the cart.
	3. Increase the number of items in the cart to 10. Verify that the total price changed.
	4. Remove a product from the cart. Verify the change.
	**/
	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		homePage = new HomePage();
	}
	
	
	/**
	 * 1. Search for a product. Verify the list.
	 */
	@Test(priority=1)
	public void searchForProductAndVerifyList(){
		testUtil.waitForJStoLoad();
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in","Home page title not matched");
		homePage.searchProduct("watch");
		testUtil.waitForJStoLoad();
		Assert.assertEquals(homePage.verifyTheList("watch"),true,"List is having different Product");
	}
	
	/**
	 * 2. Add a product to the cart. Verify that the added product is in the cart.
	 */
	@Test(priority=2)
	public void addProductToCardAndVerify(){
		String productName;
		homePage.searchProduct("watch");
		homePage.navigateToProductPage(1);
		homePage.switchTab(1);
		testUtil.waitForJStoLoad();
		productName = homePage.getProductName();
		Assert.assertTrue(homePage.validateProductPageLoaded(), "Product Page not loaded");
		homePage.clickAddToCardButton();
		homePage.navigateToShoppingCart();
		testUtil.waitForJStoLoad();
		Assert.assertTrue(homePage.getShoppingCartProductName(1).toUpperCase().contains(productName.toUpperCase()), "Product Added in Cart is not Equal");
	}
	
	/**
	 * 3. Increase the number of items in the cart to 10. Verify that the total price changed.
	 */
	@Test(priority=3)
	public void increaseItemsAndVerifyPrice(){
		double beforeAmount, afterAmount;
		homePage.searchProduct("watch");
		homePage.navigateToProductPage(1);
		homePage.switchTab(1);
		testUtil.waitForJStoLoad();
		homePage.clickAddToCardButton();
		homePage.navigateToShoppingCart();
		testUtil.waitForJStoLoad();
		beforeAmount = homePage.getTotalAmount();
		homePage.changeQtyProduct(1,"10");
		afterAmount = homePage.getTotalAmount();
		Assert.assertTrue(afterAmount>=beforeAmount,"Verify that the total price didnt changed.");
	}
	
	/**
	 * 4. Remove a product from the cart. Verify the change.
	 */
	@Test(priority=4)
	public void removeItemsAndVerifyPrice(){
		String productName;
		homePage.searchProduct("watch");
		homePage.navigateToProductPage(1);
		homePage.switchTab(1);
		productName = homePage.getProductName();
		testUtil.waitForJStoLoad();
		homePage.clickAddToCardButton();
		homePage.navigateToShoppingCart();
		testUtil.waitForJStoLoad();
		homePage.removeItemFromShoppingCart(productName);
		testUtil.waitForJStoLoad();
		homePage.validateProductRemoved(productName);
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	

}
