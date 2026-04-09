package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design login page for open care application")
@Story("LoginUserStory 200: Add Login Features with title url login page")
public class LoginPageTest extends BaseTest {
	@Description("checking login page title....")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("starting login page title test");
	Assert.assertEquals(loginPage.getLoginPageTitle(),AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageURLTest() {
	Assert.assertTrue(loginPage.getLoginPageUrl().contains(AppConstants.LOGIN_PAGE_URL));
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Issue("Bug 9001")
	@Test
	public void forgotPwdLinkExistTest() {
	Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = Integer.MAX_VALUE)  // this will run at the end of all test cases / last priority
	public void loginTest() {
		homePage=  loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		ChainTestListener.log("Home page title " + homePage.getHomePageTitle()); 
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE);
	}
}
