package com.info.Helper;

import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Browser_Helper {

	private WebDriver driver;
	private static final Logger log = Logger.getLogger(Browser_Helper.class);

	public Browser_Helper(WebDriver driver) {
		this.driver = driver;
		log.debug("Browser Helper:" + this.driver.hashCode());
	}

	public void goBack() {
		driver.navigate().back();
		log.info("User click on back button");
	}

	public void goForward() {
		driver.navigate().forward();
		log.info("Usr has click on forward button");
	}

	public void refresh() {
		driver.navigate().refresh();
		log.info("User has click on refresh");
	}

	public Set<String> getWindowHandle() {
		log.info("");
		return driver.getWindowHandles();
	}

	public void switchToWindow(int index) {

		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandle());

		if (index < 0 || index > windowsId.size())
			throw new IllegalArgumentException("Invalid Index" + index);
		driver.switchTo().window(windowsId.get(index));
		log.info(index);
	}

	public void switchToparentWindow() {
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandle());
		driver.switchTo().window(windowsId.get(0));
		log.info("");
	}

	public void switchToparentWindowWithCloseAllChildWindow() {
		LinkedList<String> windowsId = new LinkedList<String>(getWindowHandle());

		for (int i = 1; i < windowsId.size(); i++) {
			log.info(windowsId.get(i));
			driver.switchTo().window(windowsId.get(i));
			driver.close();
		}
		switchToparentWindow();
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
		log.info(nameOrId);
	}

}
