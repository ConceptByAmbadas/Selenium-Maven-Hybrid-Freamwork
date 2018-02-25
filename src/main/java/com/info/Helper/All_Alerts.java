package com.info.Helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.info.TestBase.TestBase;

public class All_Alerts extends TestBase {
	public final WebDriver driver;
	public static final Logger log = Logger.getLogger(All_Alerts.class);

	public All_Alerts(WebDriver driver) {
		this.driver = driver;
	}

	public Alert getAlert() {
		log.debug(" ");
		return driver.switchTo().alert();
	}

	public void acceptAlert() {
		log.info("----");
		getAlert().accept();
	}

	public void dismissAlert() {
		log.info("----");
		getAlert().dismiss();
	}

	public String getAlertText() {
		String text = getAlert().getText();
		log.info(text);
		return text;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			log.info("true");
			return true;

		} catch (NoAlertPresentException ex) {
			log.info("False");
			return false;
		}
	}

	public void acceptAlertIfPresent() {
		if (!isAlertPresent())
			return;
		dismissAlert();
		log.info("");
	}

	public void dismissAlertIfPresent() {
		if (!isAlertPresent())
			return;
		acceptAlert();
		log.info("");
	}

	public void acceptPrompt(String text) {
		if (!isAlertPresent())
			return;
		getAlert().sendKeys(text);
		log.info(text);
	}

}
