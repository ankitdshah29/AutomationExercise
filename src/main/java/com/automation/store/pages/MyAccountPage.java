package com.automation.store.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.automation.store.model.Account;
import com.automation.store.utils.WebDriverHelper;

/**
 * @author Ankit Shah
 *
 *         This class performs MyAccount page functionality like navigate to
 *         detail page, Add, update, delete and verification of account detail.
 *         verification of saved data etc.
 * 
 */
public class MyAccountPage {

	private WebDriver driver;

	@FindBy(xpath = "//div[@id='account']/a")
	private WebElement myAccountLink;

	@FindBy(xpath = "//div[@class='user-profile-links']/a[2]")
	private WebElement yourDetailLink;

	@FindBy(xpath = "//input[@id='wpsc_checkout_form_2']")
	private WebElement firstNameTxt;

	@FindBy(xpath = "//input[@id='wpsc_checkout_form_3']")
	private WebElement lastNameTxt;
	@FindBy(xpath = "//textarea[@id='wpsc_checkout_form_4']")
	private WebElement addressTxt;

	@FindBy(xpath = "//input[@id='wpsc_checkout_form_5']")
	private WebElement cityTxt;

	@FindBy(xpath = "//input[@id='wpsc_checkout_form_8']")
	private WebElement codeTxt;

	@FindBy(xpath = "//input[@id='wpsc_checkout_form_18']")
	private WebElement phoneTxt;

	@FindBy(xpath = "//*[@id='wpsc_checkout_form_7']")
	private WebElement countryDropDown;

	@FindBy(xpath = "//div[@class='myaccount']/form/table[3]/tbody/tr/td/input[2]")
	private WebElement saveBtn;

	@FindBy(xpath = "//*[@id='meta']/ul/li[2]/a")
	private WebElement logOutBtn;

	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Navigate to your detail tab
	 */
	public void navigateToYourDetailTab() {
		WebDriverHelper.waitForElementToBeClickable(driver, yourDetailLink, 5);
		yourDetailLink.click();
	}

	/**
	 * Add / Save Account detail
	 * 
	 * @param account
	 *            - Account object which contains
	 *            firstname,lastname,address,phone etc.
	 */
	public void addAndSaveAccountDetail(Account account) {
		setFirstName(account.getFirstName());
		setLastName(account.getLastName());
		setAddressName(account.getAddress());
		setCity(account.getCity());
		setCountry(account.getCountry());
		setPostalCode(account.getCode());
		setPhone(account.getPhone());
		savePage();
	}

	/**
	 * Logout and login back for verification of previously saved data
	 */
	public void logoutAndLoginBack() {
		WebDriverHelper.scrollDownTo(driver, myAccountLink);
		WebDriverHelper.waitForElementToBeClickable(driver, yourDetailLink, 10);
		logOutBtn.click();
	}

	/**
	 * Set first name
	 * 
	 * @param fname
	 */
	private void setFirstName(String fname) {
		firstNameTxt.clear();
		firstNameTxt.sendKeys(fname);
	}

	/**
	 * Set Last name
	 * 
	 * @param lname
	 */
	private void setLastName(String lname) {
		WebDriverHelper.waitForElementToBeClickable(driver, lastNameTxt, 5);
		lastNameTxt.clear();
		lastNameTxt.sendKeys(lname);
	}

	/**
	 * Set address
	 * 
	 * @param address
	 */
	private void setAddressName(String address) {
		WebDriverHelper.waitForElementToBeClickable(driver, addressTxt, 5);
		addressTxt.clear();
		addressTxt.sendKeys(address);
	}

	/**
	 * Set City
	 * 
	 * @param city
	 */
	private void setCity(String city) {
		WebDriverHelper.waitForElementToBeClickable(driver, cityTxt, 5);
		cityTxt.clear();
		cityTxt.sendKeys(city);
	}

	/**
	 * Set Postal code
	 * 
	 * @param code
	 */
	private void setPostalCode(String code) {
		WebDriverHelper.waitForElementToBeClickable(driver, codeTxt, 5);
		WebDriverHelper.scrollDownTo(driver, codeTxt);
		codeTxt.clear();
		codeTxt.sendKeys(code);
	}

	/**
	 * Set Phone number
	 * 
	 * @param phone
	 */
	private void setPhone(String phone) {
		WebDriverHelper.waitForElementToBeClickable(driver, phoneTxt, 5);
		phoneTxt.clear();
		phoneTxt.sendKeys(phone);
	}

	/**
	 * Select country from combo
	 * 
	 * @param country
	 */
	private void setCountry(String country) {
		Select dropdown = new Select(countryDropDown);
		dropdown.selectByValue(country);
	}

	/**
	 * Save account detail
	 */
	private void savePage() {
		saveBtn.click();

		/*
		 * Issue: Page got refresh after click on save button. after refresh,
		 * some of the fields remain blank. there is no specific field that
		 * remain blank, random fields were remain blank. After putting sleep
		 * its hardly reproduce. Note: It cannot reproduce while trying manually
		 */
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verify account detail after save account detail
	 * 
	 * @param account
	 *            - Account object
	 */
	public void verifyAcoountDetail(Account account) {
		navigateToYourDetailTab();
		WebDriverHelper.waitForElementToBeClickable(driver, firstNameTxt, 5);
		Assert.assertEquals("Failed to verify first name ",account.getFirstName(), firstNameTxt.getAttribute("value").trim());
		Assert.assertEquals("Failed to verify last name ",account.getLastName(), lastNameTxt.getAttribute("value").trim());
		Assert.assertEquals("Failed to verify address ",account.getAddress(), addressTxt.getAttribute("value").trim());
		Assert.assertEquals("Failed to verify city ",account.getCity(), cityTxt.getAttribute("value").trim());
		WebDriverHelper.waitForElementToBeClickable(driver, codeTxt, 5);
		WebDriverHelper.scrollDownTo(driver, codeTxt);
		Assert.assertEquals("Failed to verify postal code ",account.getCode(), codeTxt.getAttribute("value").trim());
		Assert.assertEquals("Failed to verify phone ", account.getPhone(), phoneTxt.getAttribute("value").trim());
	}
}