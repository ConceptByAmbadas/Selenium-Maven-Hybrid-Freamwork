package com.info.TestScripts;

//import static com.info.TestBase.TestBase.report;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.info.Driver.Driver;
import com.info.TestBase.TestBase;

public class TC_001 extends TestBase {

	public static final Logger log = Logger.getLogger(TC_001.class.getName());

	@DataProvider(name = "testData")
	public Object[][] dataSource() {
		return getTestData("Datasheet.xlsx", "Enquiry_Form");
	}

	@Test(dataProvider = "testData")
	public void Enquiry_form(String name, String email, String mobile, String Description, String runMode) throws IOException {
		test = report.startTest("Validating Post requirement form");
		log.info("---Executing TC1...");
		Driver.Instance.get(getApplicationURL());
		System.out.println("Data" + name);
		System.out.println("Data" + email);
		System.out.println("Data" + mobile);
		getScreenshot(Driver.Instance);

	}
}