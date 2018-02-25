package com.info.Helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VerificationHelper {

	private WebDriver driver;
	private static final Logger log = Logger.getLogger(VerificationHelper.class);

	public VerificationHelper(WebDriver driver) {
		this.driver = driver;
		log.debug("Verification Helper:" + this.driver.hashCode());
	}

	public static synchronized boolean verifyelementPresent(WebElement element) {
		boolean isdisplay = false;
		try {
			isdisplay = element.isDisplayed();
			log.info(element.getText() + "is display");
		} catch (Exception ex) {
			log.error("Element not found" + ex);
		}
		return isdisplay;
	}

	public static synchronized boolean verifyelementNotPresent(WebElement element) {
		boolean isdisplay = false;
		try {
			element.isDisplayed();
			log.info(element.getText() + "is display");
		} catch (Exception ex) {
			log.error("Element not found" + ex);
			isdisplay = true;
		}
		return isdisplay;
	}

	public static synchronized boolean verifyTextEqual(WebElement element, String expected_text) {
		boolean flag = false;
		try {
			String actual_text = element.getText();
			if (actual_text.equals(expected_text)) {
				log.info("Actual Text is" + actual_text + "Expected Text is" + expected_text);
				return flag = true;
			} else {
				log.error("Actual Text is" + actual_text + "Expected Text is" + expected_text);
				return flag;
			}
		} catch (Exception ex) {
			log.info("Actual Text is" + element.getText() + "Expected Text is" + expected_text);
			log.error("Text Not Matching" + ex);
			return flag;
		}

	}

}
