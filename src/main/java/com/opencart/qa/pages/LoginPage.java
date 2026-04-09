package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	//-------------- initial driver setup for all pages - common
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//-------Every page will have constructor
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//------------- private By locators   ---- Encapsulation so these cannot be accessed outside class
	// PAGE OBJECTS ARE the EXAMPLE OF ENCAPSULATION
	private  final By emailId = By.id("input-email");
	private  final By password = By.id("input-password");
	private  final By loginBtn = By.xpath("//input[@type='submit']");
	private  final By forgotPassword = By.linkText("Forgotten Password");
	
	private final By registrationLink = By.linkText("Register");
	
	// -----------public page actions/method
	@Step("getting the login page title")
	public String getLoginPageTitle() {
	String actTitle =	eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
	System.out.println("Login page title : " + actTitle);
	return actTitle;
	}
	
	@Step("getting the login page url")
	public String getLoginPageUrl() {
	String actUrl =	eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL, AppConstants.SHORT_TIME_OUT);
	System.out.println("Login page url : " + actUrl);
	return actUrl;
	}
	
	@Step("checking forgot password link")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForVisibilityOfElement(forgotPassword, AppConstants.SHORT_TIME_OUT).isDisplayed();
	}
	
	//------------Page Chaining - Login page then home page
	@Step("user is logged in with username: {0} and password: {1}") //1st parameter
	public HomePage doLogin(String userName,String pwd) {
		System.out.println("App credentials - " + userName + " " + password);
		eleUtil.doSendKeys(emailId, userName,AppConstants.SHORT_TIME_OUT);
		eleUtil.doActionsSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new HomePage(driver); // return the next landing page object
	}
	
	@Step("navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementReadyAndClick(registrationLink, AppConstants.SHORT_TIME_OUT);
		return new RegisterPage(driver);
	}
	
	
}
