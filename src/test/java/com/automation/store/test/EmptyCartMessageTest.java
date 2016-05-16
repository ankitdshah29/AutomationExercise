package com.automation.store.test;

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

/**
 * @author Ankit Shah
 *
 *         Verify removing all items from your cart produces an empty cart
 *         message.
 */
public class EmptyCartMessageTest {

	private final static String URL = "http://store.demoqa.com/tools-qa";
	private LoginPage loginPage;
	private HomePage homePage;
	private ProductPage productPage;
	private DataPropertyHelper dataPropertyHelper;
	private CartPage checkoutPage;
	private Logger log = Logger.getLogger(EmptyCartMessageTest.class.getName());

	@Before
	public void setup() {
		loginPage = PageFactory.initElements(new FirefoxDriver(), LoginPage.class);
		log.debug("Open mozila browser for '" + URL + "'");
		loginPage.open(URL);
		dataPropertyHelper = new DataPropertyHelper();
	}

	@Test
	public void emptyCartTest() {
		// Get credentials from properties file and Login
		log.debug("Reading credentials from data property file. ");
		String username = dataPropertyHelper.getProperty("login.username");
		String password = dataPropertyHelper.getProperty("login.password");
		log.debug("Login to " + URL);
		homePage = loginPage.login(username, password);
		Assert.assertNotNull("Failed to login", homePage);

		// If cart is empty, navigate to product page and select product and
		// navigate to cart page else navigate to cart page
		log.debug("Verifing item is available or not");
		if (homePage.getNumberSelectedCartItem() < 1) {
			log.debug("Navigate to product page ");
			productPage = homePage.navigateToProductPage();
			String selectPhone = dataPropertyHelper.getProperty("search.iphone");
			log.debug("Select iphone '" + selectPhone + "' and navigate to cart page.");
			checkoutPage = productPage.selectPhone(selectPhone);
		} else {
			log.debug("Navigating to cart page ");
			checkoutPage = homePage.navigateToCartPage();
		}

		// Remove items from cart and verify for empty message
		log.debug("Removing item from cart");
		checkoutPage.removeItemsFromCart();
		Assert.assertTrue("Empty message not found..", checkoutPage.verifyEmptyMessage());
		log.debug("Item removed.");
	}

	@After
	public void tearDown() {
		loginPage.quit();
	}
}
