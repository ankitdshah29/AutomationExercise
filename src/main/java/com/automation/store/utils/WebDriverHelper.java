package com.automation.store.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {

	/**
	 * Explicit wait for element to be clickable
	 * 
	 * @param driver
	 *            - WebDriver
	 * @param element
	 *            - WebDriver
	 * @param time
	 *            - Wait time
	 */
	public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Scroll down to element
	 * 
	 * @param driver
	 *            - WebDriver
	 * @param element
	 *            - WEbElement
	 */
	public static void scrollDownTo(WebDriver driver, WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
