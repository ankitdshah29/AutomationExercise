package com.automation.store.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.automation.store.pages.CartPage;
import com.automation.store.pages.HomePage;
import com.automation.store.pages.LoginPage;
import com.automation.store.pages.ProductPage;
import com.automation.store.utils.DataPropertyHelper;

public class OrderTest {

	private final static String URL = "http://store.demoqa.com/tools-qa";
	private DataPropertyHelper dataPropertyHelper;
	private LoginPage loginPage;
	private HomePage homePage;
	private ProductPage productPage;
	private CartPage checkoutPage;
	private Logger log = Logger.getLogger(EmptyCartMessageTest.class.getName());

	@Before
	public void setup() {
		BasicConfigurator.configure();
		loginPage = PageFactory.initElements(new FirefoxDriver(), LoginPage.class);
		log.debug("Open mozila browser for '" + URL + "'");
		loginPage.open(URL);
		dataPropertyHelper = new DataPropertyHelper();
	}

	@Test
	public void purchaseIphoneTest() {
		// Get credentials from properties file and Login
		log.debug("Reading credentials from data property file. ");
		String username = dataPropertyHelper.getProperty("login.username");
		String password = dataPropertyHelper.getProperty("login.password");
		log.debug("Login to " + URL);
		homePage = loginPage.login(username, password);
		Assert.assertNotNull("Failed to login", homePage);

		// Navigate to product page
		log.debug("Navigating to product page");
		productPage = homePage.navigateToProductPage();
		Assert.assertNotNull("Failed to navigate to product page", productPage);

		// get the iPhone title from properties file and select the phone
		String selectPhone = dataPropertyHelper.getProperty("search.iphone");
		log.debug("Selecting iPhone '" + selectPhone + "'");
		checkoutPage = productPage.selectPhone(selectPhone);
		Assert.assertNotNull("Failed to navigate to checkout page", checkoutPage);

		// Navigate to shipping tab and verify the price and total
		log.debug("Navigating to Shipping page");
		checkoutPage.navigateTOShippingTab();
		Assert.assertTrue("Failed to navigate to Shipping tab", checkoutPage.isShippingTab());
		Assert.assertTrue("Failed to select country", checkoutPage.selectCountry("US"));
		log.debug("Verifying item cost and total");
		Assert.assertTrue("Actual price $" + checkoutPage.getSelectPhonePrice() + " and checkout page item price $"
				+ checkoutPage.getItemCost() + " does not match", checkoutPage.isSamePriceAsActualPrice());
		Assert.assertTrue("Total price does not matching.", checkoutPage.verifyTotalPrice());
		log.debug("Item cost and total verified.");
	}

	@After
	public void teardown() throws InterruptedException {
		loginPage.quit();
	}

}
