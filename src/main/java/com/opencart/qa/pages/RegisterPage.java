package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;
import com.opencart.qa.utils.StringUtil;

public class RegisterPage {

	//-------------- initial driver setup for all pages - common
		private WebDriver driver;
		private ElementUtil eleUtil;
		
		//-------Every page will have constructor
		
		public RegisterPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
		}
		
		private final By firstName = By.id("input-firstname");
		private final By lastName = By.id("input-lastname");
		private final By email = By.id("input-email");
		private final By telephone = By.id("input-telephone");
		private final By password = By.id("input-password");
		private final By confirmPassword = By.id("input-confirm");
		
		private final By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input");
		private final By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input");
		
		private final By agree = By.xpath("//input[@name='agree']");
		private final By continueBtn = By.xpath("//input[@value='Continue']");
		
		private final By successMsg = By.cssSelector("div#content h1");
		
		private final By logout = By.linkText("Logout");
		private final By registerLink = By.linkText("Register");
		
		public String getRandomEmailId() {
			return "auto"+System.currentTimeMillis()+"@nal.com";
		}
		
		public boolean userRegistration(String firstName, String lastName,
				String telephone, String password, String subscribe) {
			eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.SHORT_TIME_OUT).sendKeys(firstName);
			eleUtil.doSendKeys(this.lastName, lastName);
			eleUtil.doSendKeys(this.email, StringUtil.getRandomEmailId());
			eleUtil.doSendKeys(this.telephone, telephone);
			eleUtil.doSendKeys(this.password, password);
			eleUtil.doSendKeys(this.confirmPassword, password);
			
			if(subscribe.equalsIgnoreCase("yes")) {
				eleUtil.doClick(subscribeYes);
			} else {
				eleUtil.doClick(subscribeNo);
			}
			
			eleUtil.doClick(agree);
			eleUtil.doClick(continueBtn);
			
			if(eleUtil.waitForVisibilityOfElement(successMsg, AppConstants.SHORT_TIME_OUT).getText().contains(AppConstants.REGISTER_SUCCESS))
			{
				eleUtil.doClick(logout);
				eleUtil.doClick(registerLink);
				return true;
			}
			return false;
		}
		
}
