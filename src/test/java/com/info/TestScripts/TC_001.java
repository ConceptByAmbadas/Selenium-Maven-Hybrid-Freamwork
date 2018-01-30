package com.info.TestScripts;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.info.Driver.Driver;
import com.info.TestBase.TestBase;

public class TC_001 extends TestBase {

	@DataProvider(name = "testData")
	public Object[][] dataSource() {
		return getTestData("Datasheet.xlsx", "Enquiry_Form");
	}

	@Test(dataProvider = "testData")
	public void Enquiry_form(String name, String email, String mobile, String Description, String runMode) throws IOException {
		Driver.Instance.get(getApplicationURL());
		System.out.println("Data" + name);
		System.out.println("Data" + email);
		System.out.println("Data" + mobile);
		getScreenshot(Driver.Instance);

	}
}