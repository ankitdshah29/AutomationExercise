package com.automation.store.test;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.automation.store.model.Account;
import com.automation.store.pages.HomePage;
import com.automation.store.pages.LoginPage;
import com.automation.store.pages.MyAccountPage;
import com.automation.store.utils.DataPropertyHelper;

/**
 * @author Ankit Shah
 *
 */
public class AccountDetailTest {

	private final static String URL = "http://store.demoqa.com/tools-qa";
	private DataPropertyHelper dataPropertyHelper;
	private LoginPage loginPage;
	private HomePage homePage;
	private MyAccountPage myAccountPage;
	private Logger log = Logger.getLogger(AccountDetailTest.class.getName());

	@Before
	public void setup() {
		loginPage = PageFactory.initElements(new FirefoxDriver(), LoginPage.class);
		log.debug("Open mozila browser for '" + URL + "'");
		loginPage.open(URL);
		dataPropertyHelper = new DataPropertyHelper();
	}

	@Test
	public void MyAccountDetailTest() {
		// Get credentials from properties file and Login
		log.debug("Reading credentials from data property file. ");
		String username = dataPropertyHelper.getProperty("login.username");
		String password = dataPropertyHelper.getProperty("login.password");
		log.debug("Login to " + URL);
		homePage = loginPage.login(username, password);
		Assert.assertNotNull("Failed to login", homePage);

		// Navigate to my Account and account detail page
		log.debug("Navigating to my account page");
		myAccountPage = homePage.navigateToAccoutPage();
		Assert.assertNotNull("Failed to navigate to my account page", myAccountPage);
		myAccountPage.navigateToYourDetailTab();

		// Read account detail from properties file and set to account object
		Account account = getAccountDetailFromProperties();

		// Add/update, save and verify account detail
		log.debug("Adding and saving account detail");
		myAccountPage.addAndSaveAccountDetail(account);
		log.debug("verifying account detail");
		myAccountPage.verifyAcoountDetail(account);
		log.debug("Account detail verified");
		// Logout and login back to account detail and verify last save account
		// detail
		log.debug("Loging out and login back");
		myAccountPage.logoutAndLoginBack();
		homePage = loginPage.login(username, password);
		log.debug("Navigating to my account page");
		myAccountPage = homePage.navigateToAccoutPage();
		log.debug("Verifying account detail");
		myAccountPage.verifyAcoountDetail(account);
		log.debug("Account detail verified");
	}

	@After
	public void tearDown() {
		loginPage.quit();
	}

	/**
	 * Read account details from properties file and return account object
	 * 
	 * @return - Account Object
	 */
	private Account getAccountDetailFromProperties() {
		Account account = new Account();
		account.setFirstName(dataPropertyHelper.getProperty("account.firstname"));
		account.setLastName(dataPropertyHelper.getProperty("account.lastname"));
		account.setAddress(dataPropertyHelper.getProperty("account.address"));
		account.setCity(dataPropertyHelper.getProperty("account.city"));
		account.setCountry(dataPropertyHelper.getProperty("account.country"));
		account.setPhone(dataPropertyHelper.getProperty("account.phone"));
		account.setCode(dataPropertyHelper.getProperty("account.code"));
		return account;
	}
}
