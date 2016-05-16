package com.automation.store.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.automation.store.utils.WebDriverHelper;

/**
 * @author Ankit Shah
 *
 *         This class performs Cart page functionality like navigate to shipping
 *         page, calculation and verification of price, remove item from cart
 *         etc
 * 
 */
public class CartPage {

	private WebDriver driver;
	private String selectPhonePrice;
	private final static String SHIPPING_TAB_HEADING = "Calculate Shipping Price";
	private final static String EMPTY_CART_MESSAGE = "Oops, there is nothing in your cart.";

	@FindBy(css = "#checkout_page_container .step2")
	private WebElement checkoutBtn;

	@FindBy(css = "#wpsc_shopping_cart_container>h2")
	private WebElement shippingHeading;

	@FindBy(id = "current_country")
	private WebElement countryDropDown;

	@FindBy(xpath = ".//div[@class='review group']/table/tbody/tr[2]/td[2]")
	private WebElement shippingCost;

	@FindBy(xpath = ".//div[@class='review group']/table/tbody/tr[3]/td[2]")
	private WebElement itemCost;

	@FindBy(xpath = ".//div[@class='review group']/table/tbody/tr[4]/td[2]")
	private WebElement tax;

	@FindBy(xpath = ".//div[@class='review group']/table/tbody/tr[5]/td[2]")
	private WebElement totalCost;

	@FindBy(xpath = "//table[@class='checkout_cart']//td[6]/form/input[4]")
	private List<WebElement> removeBtns;

	@FindBy(className = "entry-content")
	private WebElement emptyMessageDiv;

	@FindBy(xpath = ".//div[@id='checkout_page_container']/div[1]/table/tbody/tr/td[5]/span/span")
	private List<WebElement> listOfItemPrice;

	public CartPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Navigate to shipping tab
	 */
	public void navigateTOShippingTab() {
		setTotalOfSelectedItemInCart();
		checkoutBtn.click();
	}

	/**
	 * Set total price for selected items from the cart
	 */
	private void setTotalOfSelectedItemInCart() {
		WebDriverHelper.waitForElementToBeClickable(driver, listOfItemPrice.get(0), 5);
		float total = 0.00f;
		for (WebElement element : listOfItemPrice) {
			total += Float.parseFloat(element.getText().trim().replace("$", "").replace(",", ""));
		}

		setSelectPhonePrice(String.format("%.02f", total));
	}

	/**
	 * Verify current page is shipping page
	 * 
	 * @return - True/False
	 */
	public Boolean isShippingTab() {
		WebDriverHelper.waitForElementToBeClickable(driver, shippingHeading, 5);
		return (SHIPPING_TAB_HEADING.equals(shippingHeading.getText().trim()));
	}

	/**
	 * Select country
	 * 
	 * @param country
	 *            - Country
	 * @return - True/False
	 */
	public boolean selectCountry(String country) {
		Select dropdown = new Select(countryDropDown);
		dropdown.selectByValue(country);
		return (country.equals(dropdown.getFirstSelectedOption().getAttribute("value")));
	}

	/**
	 * Get price for selected phone
	 * 
	 * @return - price
	 */
	public String getSelectPhonePrice() {
		return selectPhonePrice;
	}

	/**
	 * Set price for selected phone
	 * 
	 * @param selectPhonePrice
	 */
	public void setSelectPhonePrice(String selectPhonePrice) {
		this.selectPhonePrice = selectPhonePrice;
	}

	/**
	 * Verification of price with actual price when adding to cart with checkout
	 * price
	 * 
	 * @return - True/False
	 */
	public boolean isSamePriceAsActualPrice() {
		WebDriverHelper.scrollDownTo(driver, itemCost);
		return (selectPhonePrice.equalsIgnoreCase(itemCost.getText().trim().replace("$", "").replace(",", "")));
	}

	/**
	 * Get Item cost at checkout time
	 * 
	 * @return - Price
	 */
	public String getItemCost() {
		return itemCost.getText().trim().replace("$", "");
	}

	/**
	 * This method is used for comparing Product price with actual price and
	 * verify total price
	 */
	public boolean verifyTotalPrice() {
		float sCost = Float.parseFloat(shippingCost.getText().trim().replace("$", "").replace(",", ""));
		float iCost = Float.parseFloat(itemCost.getText().trim().replace("$", "").replace(",", ""));
		float taxCost = Float.parseFloat(tax.getText().trim().replace("$", "").replace(",", ""));
		float tCost = Float.parseFloat(totalCost.getText().trim().replace("$", "").replace(",", ""));
		return (tCost == sCost + iCost + taxCost);
	}

	/**
	 * Remove items from cart
	 */
	public void removeItemsFromCart() {
		for (int i = removeBtns.size() - 1; i >= 0; i--) {
			WebElement element = removeBtns.get(i);
			WebDriverHelper.waitForElementToBeClickable(driver, element, 5);
			element.click();
		}
	}

	/**
	 * Verification of empty message
	 * 
	 * @return - True / False
	 */
	public boolean verifyEmptyMessage() {
		WebDriverHelper.waitForElementToBeClickable(driver, emptyMessageDiv, 5);
		return EMPTY_CART_MESSAGE.equals(emptyMessageDiv.getText().trim());
	}
}
