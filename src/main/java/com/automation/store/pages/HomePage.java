package com.automation.store.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Ankit Shah
 * 
 *         This class performs home page functionality like navigation to
 *         Account page, product page, cart page, online store page. Verify
 *         current page is Home page etc.
 * 
 */
public class HomePage {

	private WebDriver driver;

	@FindBy(xpath = "//li[@id='wp-admin-bar-site-name']/a")
	private WebElement storeLink;

	@FindBy(xpath = "//div[@id='account']/a")
	private WebElement myAccountLink;

	@FindBy(xpath = "//*[@id='header_cart']/a/em[1]")
	private WebElement totalSelectedItemInCart;

	@FindBy(xpath = ".//*[@id='header_cart']/a/span[1]")
	private WebElement cartPageLink;

	@FindBy(css = "#logo>img")
	private WebElement logo;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * After successfully logged in by default it opens wordpress dashboard
	 * Navigate to online store page.
	 */
	private void navigateToStorePage() {
		storeLink.click();
	}

	/**
	 * Navigate to product page
	 * 
	 * @return - Object of product page
	 */
	public ProductPage navigateToProductPage() {
		if (!isHomePage()) {
			navigateToStorePage();
		}
		return PageFactory.initElements(driver, ProductPage.class);
	}

	/**
	 * Navigate to My Account page
	 * 
	 * @return - Object of my account page
	 */
	public MyAccountPage navigateToAccoutPage() {
		if (!isHomePage()) {
			navigateToStorePage();
		}
		myAccountLink.click();
		return PageFactory.initElements(driver, MyAccountPage.class);
	}

	/**
	 * Get total number of items is available for checkout
	 * 
	 * @return - Total number of items for checkout
	 */
	public int getNumberSelectedCartItem() {
		if (!isHomePage()) {
			navigateToStorePage();
		}
		return Integer.parseInt(totalSelectedItemInCart.getText().trim());
	}

	/**
	 * Navigate to cart page
	 * 
	 * @return - Object of cart page
	 */
	public CartPage navigateToCartPage() {
		if (!isHomePage()) {
			navigateToStorePage();
		}
		cartPageLink.click();
		return PageFactory.initElements(driver, CartPage.class);
	}

	/**
	 * Verify that current page is home page or not
	 * 
	 * @return - True/False
	 */
	private boolean isHomePage() {
		try {
			if (logo.getAttribute("src").toString().contains("Tools-QA-2.png")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
