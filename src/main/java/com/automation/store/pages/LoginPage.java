package com.automation.store.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.store.utils.WebDriverHelper;

/**
 * @author Ankit Shah
 *
 *         This class performs login functionality
 *
 */
public class LoginPage {

	private WebDriver driver;

	@FindBy(id = "user_login")
	private WebElement usernameTxt;

	@FindBy(id = "user_pass")
	private WebElement passowrdTxt;

	@FindBy(id = "wp-submit")
	private WebElement loginBtn;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Open website in browser
	 * 
	 * @param url
	 *            - URL
	 */
	public void open(String url) {
		driver.get(url);
	}

	/**
	 * Quit browser
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 * Login to "http://store.demoqa.com/tools-qa/"
	 * 
	 * @param username
	 *            - User name
	 * @param password
	 *            - Password
	 * @return Object of Homepage
	 */
	public HomePage login(String username, String password) {
		WebDriverHelper.waitForElementToBeClickable(driver, usernameTxt, 10);
		usernameTxt.sendKeys(username);
		passowrdTxt.sendKeys(password);
		loginBtn.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
}
