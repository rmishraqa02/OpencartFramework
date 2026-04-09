package com.opencart.qa.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
private WebDriver driver;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	public void navigateToSpecificPage(String pageNum) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go('"+pageNum+"')");
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for(int i=0;i<7;i++) {
			changeColor("rgb(0,200,0)",element);
			changeColor(bgcolor,element);
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'",element);
		try {
			Thread.sleep(20);
		}catch (InterruptedException e) {
		}
	}
}
