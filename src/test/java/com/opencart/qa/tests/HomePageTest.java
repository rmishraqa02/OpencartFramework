package com.opencart.qa.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

public class HomePageTest extends BaseTest{
	
	
	@BeforeClass
	public void homePageSetup() {
		homePage =	loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}

	@Test
	public void homePageTitleTest() {
		
		String homePageActTitle =	homePage.getHomePageTitle();
		Assert.assertEquals(homePageActTitle, AppConstants.HOME_PAGE_TITLE);
	}
	
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist());
	}
	
	@Test
	public void headersTest() {
	List<String> actualHeadersList = homePage.getHomePageHeaders();
	Assert.assertEquals(actualHeadersList, AppConstants.EXPECTED_HEADERS_LIST); //comparing collections
	}
	
	@DataProvider
	public Object[][] getSearchTestData() {
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"canon",1},
			{"samsung",2}
		};
	}
	
	
	@Test(dataProvider = "getSearchTestData")
	public void searchTest(String searchKey, int expectedResultCount) {
	resultsPage = 	homePage.doSearch(searchKey);
	Assert.assertEquals(resultsPage.getCountofSearchResults(),expectedResultCount);
	}
	
}
