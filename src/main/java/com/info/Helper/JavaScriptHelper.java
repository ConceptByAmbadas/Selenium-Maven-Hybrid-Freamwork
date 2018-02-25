package com.info.Helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class JavaScriptHelper {

	public static WebDriver driver;
	public static final Logger log = Logger.getLogger(JavaScriptHelper.class);

	public JavaScriptHelper(WebDriver driver) {
		this.driver = driver;
		log.debug("Java Script helper" + this.driver.hashCode());
	}

	public Object executeScript(String scripts) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		log.info(scripts);
		return exe.executeScript(scripts);
	}

	public Object executeScript(String script, Object... args) {

		JavaScriptExecutor exe = (JavaScriptExecutor) driver;
		log.info(script);
		return ((JavascriptExecutor) exe).executeScript(script, args);
	}

	public void scrollToElement(WebElement element) {
		executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
		log.info(element);
	}

	public void scrollToElementandClick(WebElement element) {
		scrollToElement(element);
		element.click();
		log.info(element);
	}

	public void scrollIntoView(WebElement element) {
		executeScript("argument[0].scrollIntoView", element);
		log.info(element);
	}

	public void scrollIntoViewAndClick(WebElement element) {
		scrollIntoView(element);
		element.click();
		log.info(element);
	}

	public void scrollDownVertically(WebElement element) {
		executeScript("window.ScrollTo(0,document.body.scrollHeight)");
	}

	public void scrollUpVertically(WebElement element) {
		executeScript("window.ScrollTo(0,-document.body.scrollHeight)");
	}

	public void scrollDownBypixel(WebElement element) {
		executeScript("window.scrollBy(0,1500))");
	}

	public void scrollUpBypixel(WebElement element) {
		executeScript("window.scrollBy(0,-1500))");
	}

	public void zoomByPercentage() {
		executeScript("document.body.style.zoom='40%'");
	}

	public void zoomBy100Percentage() {
		executeScript("document.body.style.zoom='100%'");
	}
}
