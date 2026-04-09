package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//-------Every page will have constructor
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private final By productSearchCount = By.cssSelector("div.product-thumb");
	
	
	public int getCountofSearchResults() {
		int count =	eleUtil.waitForAllElementsVisible(productSearchCount, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Count of total no of products -> " + count);
		return count;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Select product name: " + productName);
		eleUtil.doClick(By.linkText(productName)); //dynamic locator
		return new ProductInfoPage(driver);
	}
	
}
