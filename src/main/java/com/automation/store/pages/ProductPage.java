package com.automation.store.pages;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.store.utils.WebDriverHelper;

/**
 * @author Ankit Shah
 *
 *         This class performs Product page functionality like navigate to
 *         purchase iPhone page, select iPhone etc.
 */
public class ProductPage {

	private WebDriver driver;
	private static final String IPHONE_TITLE = "iPhones";
	// private String selectePhonePrice;

	@FindBy(xpath = "//li[@id='wp-admin-bar-site-name']/a")
	private WebElement storeLink;

	@FindBy(xpath = "//a[contains(text(),'Product Category')]")
	private WebElement productCategory;

	@FindBy(xpath = "//a[contains(text(),'iPhones')]")
	private WebElement iphoneLink;

	@FindBy(className = "entry-title")
	private WebElement iPhoneTitle;

	@FindBy(className = "productcol")
	private List<WebElement> iphoneList;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Navigate to purchase iPhone page
	 */
	private void navigateToPurchaseIphonePage() {
		Actions action = new Actions(driver);
		action.moveToElement(productCategory).moveToElement(iphoneLink).click().build().perform();

	}

	/**
	 * Verify for iPhone Page
	 * 
	 * @return - True / False
	 */
	public boolean isIphonePage() {
		return IPHONE_TITLE.equalsIgnoreCase(iPhoneTitle.getText().trim());
	}

	/**
	 * Select iPhone
	 * 
	 * @param phoneTitle
	 * @return
	 */
	public CartPage selectPhone(String phoneTitle) {
		navigateToPurchaseIphonePage();
		Assert.assertTrue("Failed to navigate to iphone page", isIphonePage());

		Iterator<WebElement> ir = iphoneList.iterator();
		boolean isFound = false;
		while (ir.hasNext()) {
			WebElement element = ir.next();
			WebDriverHelper.scrollDownTo(driver, element);
			if (phoneTitle.equals(element.findElement(By.className("prodtitle")).getText().trim())) {
				isFound = true;
				selectPhone(phoneTitle, element);
				break;
			}
		}
		if (isFound) {
			CartPage cartPage = PageFactory.initElements(driver, CartPage.class);
			return cartPage;
		} else {
			return null;
		}
	}

	/**
	 * Select iPhone
	 * 
	 * @param phoneTitle
	 * @param element
	 */
	private void selectPhone(String phoneTitle, WebElement element) {
		element.findElement(By.className("wpsc_buy_button")).click();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement popup = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#fancy_notification_content>span")));
		popup.getText().contains(phoneTitle);
		driver.findElement(By.className("go_to_checkout")).click();
	}

}
