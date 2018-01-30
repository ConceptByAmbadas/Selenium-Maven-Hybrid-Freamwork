package com.info.Driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.info.TestBase.TestBase;

public class Driver extends TestBase {

	public static WebDriver Instance = null;

	public static void initialize() {
		try {
			if (Instance == null) {
				System.out.println("Initializing Driver...");
				System.out.println("Driver Name is:-" + getDriverName());
				if (getDriverName().equalsIgnoreCase("Firefox")) {
					Instance = new FirefoxDriver();
				} else if (getDriverName().equalsIgnoreCase("Chrome")) {

					System.setProperty("webdriver.chrome.driver", getDriverpath());
					Instance = new ChromeDriver();

				} else if (getDriverName().equalsIgnoreCase("IE")) {
					Instance = new InternetExplorerDriver();

				}
				Instance.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);
				Instance.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				Instance.manage().window().maximize();
			}
		} catch (Exception ex) {
			System.out.println("exception in driver class is" + ex.getMessage());
		}
	}

	public void closeBrowser() {
		Instance.close();
	}

	public void quiteDriver() {
		Instance.quit();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Driver d1 = new Driver();
		d1.initialize();
	}
}
