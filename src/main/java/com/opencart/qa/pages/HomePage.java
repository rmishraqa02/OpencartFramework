package com.opencart.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class HomePage {
	//-------------- initial driver setup for all pages - common
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//-------Every page will have constructor
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	private final By logoutLink = By.linkText("Logout");
	private final By headers = By.xpath("//div[@id='content']/h2");
	private final By searchTextField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");
	
	public String getHomePageTitle() {
		String actTitle =	eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Home page title : " + actTitle);
		return actTitle;
		}
	
	public List<String> getHomePageHeaders() {
	List<WebElement> headerslist = 	eleUtil.waitForAllElementsPresence(headers, AppConstants.SHORT_TIME_OUT);
	List<String> headersValueList = new ArrayList<String>();	
	for(WebElement e : headerslist) {
			String headerText = e.getText();
			headersValueList.add(headerText);
		}
	return headersValueList;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.isElementDisplayed(logoutLink);
	}
	

	public ResultsPage doSearch(String searchKey) {
		System.out.println("Search key: " + searchKey);
		eleUtil.doSendKeys(searchTextField, searchKey, AppConstants.SHORT_TIME_OUT);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
		//TDD - on the basis of test need we need to develop the source code
	}
}
