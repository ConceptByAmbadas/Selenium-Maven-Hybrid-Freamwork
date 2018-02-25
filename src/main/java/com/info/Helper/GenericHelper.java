package com.info.Helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GenericHelper {

	public static WebDriver driver;
	public static final Logger log = Logger.getLogger(GenericHelper.class);

	public GenericHelper(WebDriver driver) {
		this.driver = driver;
	}

	public static synchronized String getElementText(WebElement element) {
		if (element == null) {
			log.info("Web element is null");
			return null;
		}
		boolean displayed = false;
		try {
			displayed = isDisplayed(element);
		} catch (Exception ex) {
			log.error(ex);
			return null;
		}
		if (!displayed) {
			return null;
		}
		String text = element.getText();
		log.info("webelement value is" + text);
		return text;
	}

	public static synchronized String readValueFromAttribute(WebElement element) {
		if (element == null) {
			log.info("Web element is null");
			return null;
		}
		if (!isDisplayed(element))
			return null;
		String value = element.getAttribute("value");
		log.info("Element value is" + value);
		return value;
	}

	private static boolean isDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("Element is diplayed" + element);
			return true;
		} catch (Exception ex) {
			log.info(ex);
			return false;
		}
	}

}
