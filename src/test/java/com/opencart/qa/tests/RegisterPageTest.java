package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;

public class RegisterPageTest extends BaseTest {

	
	@BeforeClass
	public void registrationSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	
	@DataProvider
	public Object[][] getUserTestData() {
		return new Object[][] {
			{"Rajesh", "Mishra", "876312", "rm@123", "yes"},
			{"Rajesh", "Kumar", "97402", "rm@1234", "yes"},
			{"Rajesh", "M", "82496", "rm@1235", "No"}
		};
	}
	
	@Test(dataProvider = "getUserTestData")
	public void userRegisterTest(String firstName,String lastName,
			String telephone,String password,String subscribe) {
		Assert.assertTrue(registerPage.userRegistration(firstName,lastName,telephone,password,subscribe));
		
	
	}
}
