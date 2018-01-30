package com.info.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.info.DataReader.Excel_Reader;
import com.info.Driver.Driver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public static Properties prop;
	public static String dest;
	public static String time;

	public ExtentReports report;
	public ExtentTest test;
	File src;
	FileInputStream fis;
	Excel_Reader reader;

	public TestBase() {
		try {
			src = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\info\\Config\\config.properties");
			fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
			log.info("---Loding Config.properties...");
			src = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\info\\Locators\\locators.properties");
			fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
			log.info("---Loding locators.properties...");
			System.out.println("Load property file....!");
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
	}

	@BeforeClass
	public void setUp() {
		try {
			Driver.initialize();
			String logpath = "log4j.properties";
			PropertyConfigurator.configure(logpath);
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
	}

	@BeforeSuite
	public void Reportsetup() {
		try {
			Calendar calender = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			report = new ExtentReports(System.getProperty("user.dir") + "\\src\\main\\java\\com\\info\\Reports\\" + System.currentTimeMillis() + ".html", true);
			report.addSystemInfo("HostName", "Pravin").addSystemInfo("Environment", "SIT").addSystemInfo("User", "Ambadas").addSystemInfo("Project Name", "Propchilli.com");
			report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

		} catch (Exception ex) {
			System.out.println("Issue is" + ex.getMessage());
		}
	}

	public void getResults(ITestResult result) {
		String screenshotpath = getScreenshot(Driver.Instance);

		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Pass is" + result.getName());
			test.log(LogStatus.PASS, "Below is the Screenshot" + test.addScreenCapture(screenshotpath));
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case Fail is" + result.getName());
			test.log(LogStatus.FAIL, "Fail exception is" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case skip is" + result.getName());
		}
	}

	@BeforeMethod
	public void beforeMethod(ITestResult result) throws IOException {
		test = report.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + "Test Started");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		getResults(result);
	}

	@AfterClass
	public void endTest() {
		// Driver.Instance.quit();
		report.endTest(test);
		report.flush();
	}

	public static String getDriverName() {
		String browser_Name = prop.getProperty("Driver");
		return browser_Name;
	}

	public static String getDriverpath() {
		String driverpath = prop.getProperty("chromeDriver");
		return driverpath;
	}

	public static String getApplicationURL() {
		String URL = prop.getProperty("URL");
		// System.out.println("URL" + URL);
		return URL;
	}

	public WebElement waitForElement(WebDriver driver, long time1, WebElement element) {
		WebDriverWait wait1 = new WebDriverWait(driver, time1);
		return wait1.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitForElementwithpollingInterval(WebDriver driver, long time2, WebElement element) {
		WebDriverWait wait2 = new WebDriverWait(driver, time2);
		wait2.pollingEvery(5, TimeUnit.SECONDS);
		wait2.ignoring(NoSuchElementException.class);
		return wait2.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void implicit_wait(long time) {
		WebDriver driver = null;
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public WebElement getLocator(String locator) throws Exception {
		System.out.println("Locator Type:-" + locator);
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		if (locatorType.toLowerCase().equals("id"))
			return Driver.Instance.findElement(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return Driver.Instance.findElement(By.name("locatorValue"));
		else if (locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class"))
			return Driver.Instance.findElement(By.className(locatorValue));
		else if (locatorType.toLowerCase().equals("tag") || locatorType.toLowerCase().equals("tagname"))
			return Driver.Instance.findElement(By.tagName(locatorValue));
		else if (locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link"))
			return Driver.Instance.findElement(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext") || locatorType.toLowerCase().equals("partiltext"))
			return Driver.Instance.findElement(By.partialLinkText(locatorValue));
		else if (locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css"))
			return Driver.Instance.findElement(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return Driver.Instance.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown Exception Type ' " + locatorType + " ' ");

	}

	public List<WebElement> getLocators(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		if (locatorType.toLowerCase().equals("id"))
			return Driver.Instance.findElements(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return Driver.Instance.findElements(By.name("locatorValue"));
		else if (locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class"))
			return Driver.Instance.findElements(By.className(locatorValue));
		else if (locatorType.toLowerCase().equals("tag") || locatorType.toLowerCase().equals("tagname"))
			return Driver.Instance.findElements(By.tagName(locatorValue));
		else if (locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link"))
			return Driver.Instance.findElements(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext") || locatorType.toLowerCase().equals("partiltext"))
			return Driver.Instance.findElements(By.partialLinkText(locatorValue));
		else if (locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css"))
			return Driver.Instance.findElements(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return Driver.Instance.findElements(By.xpath(locatorValue));
		else

			throw new Exception("Unknown Exception Type ' " + locatorType + " ' ");
	}

	public WebElement getWebElement(String locator) throws Exception {
		return getLocator(prop.getProperty(locator));

	}

	public String getScreenshot(WebDriver driver) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			Date date = new Date();
			// System.out.println(dateFormat.format(date)); // 2016/11/16
			// 12:08:43
			time = dateFormat.format(date);
			// System.out.println("Time is" + time);
			TakesScreenshot tc = (TakesScreenshot) driver;
			File src = tc.getScreenshotAs(OutputType.FILE);
			dest = System.getProperty("user.dir") + "\\src\\main\\java\\com\\info\\Screenshot\\" + time + ".png";
			File destination = new File(dest);
			FileUtils.copyFile(src, destination);
			// System.out.println("image destination" + dest);
			System.out.println("Screen shot taken...!");
		} catch (Exception ex) {
			System.out.println("Exception is" + ex.getMessage());
		}
		return dest;
	}

	public String[][] getTestData(String excel_Name, String sheetName) {
		String excel_location = System.getProperty("user.dir") + "\\src\\main\\java\\com\\info\\TestDataFile\\" + excel_Name;
		// System.out.println("DataSheet Location....!" + excel_location);
		reader = new Excel_Reader();
		return reader.getExcelData(excel_location, sheetName);
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TestBase base = new TestBase();
		base.getDriverName();
		base.getDriverpath();
		// base.getWebElement("password");
		// base.getTestData("Datasheet.xlsx", "Enquiry_Form");

	}

}
