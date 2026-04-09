package com.opencart.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencart.qa.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	
	private WebDriver driver;
	private Actions action; 
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		action = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	//never add static - as it prevents the parallel execution

	public  WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public  WebElement getElement(By locator,long timeOut) {
		try {
		return driver.findElement(locator);
		}catch(NoSuchElementException e) {
			System.out.println("Element is not found");
			e.printStackTrace();
			return waitForVisibilityOfElement(locator, timeOut);
		}
		
	}

	public String getElementAttribute(By locator,String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}
	
	public  List<WebElement> getElements(By locator){
		return driver.findElements(locator);
	}
	
	
	public  void doSendKeys(By locator,String value) {
		 doClear(locator);
		 getElement(locator).sendKeys(value);
	}
	
	public void doClear(By locator) {
		getElement(locator).clear();
	}
	@Step("entering the value: {1} using By locator; {0}")
	public  void doSendKeys(By locator,String value,long timeOut) {
		 doClear(locator);
		 getElement(locator,timeOut).sendKeys(value);
	}
	@Step("clicking on element using By locator: {0}")
	public  void doClick(By locator) {
		getElement(locator).click();
	}
	
	public  void doClick(By locator,long timeOut) {
		getElement(locator,timeOut).click();
	}
	
	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}
	
	public  boolean isElementDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public  int getElementsCount(By locator) {
		return getElements(locator).size();
	}
	
	public  List<String> getElementsTextList(By locator) {
	List<WebElement> eleList = 	getElements(locator);
	List<String> eleTextList = new ArrayList<String>();
	for(WebElement e : eleList) {
		String text = e.getText();
		if(text.length() !=0) {
			eleTextList.add(text);
		}
	}
	return eleTextList;
	}
	
	//-------------- dropdown utilities --------------
	
	public  void dropdownSelectByIndex(By locator,int index) {
		getSelect(locator).selectByIndex(index);
	}
	
	public  void dropdownSelectByVisibleText(By locator,String visibleText) {
		getSelect(locator).selectByVisibleText(visibleText);
	}
	
	public  void dropdownSelectByValue(By locator,String optionValue) {
		getSelect(locator).selectByValue(optionValue);
	}
	
	private  Select getSelect(By locator) {
		return new Select(getElement(locator));
	}
	
	public  int getDropDownOptions(By locator) {
		return getSelect(locator).getOptions().size();
	}
	
	public  List<String> getDropDownTextLists(By locator) {
		List<WebElement> optionsList = getSelect(locator).getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}
	
	//----------------- actions utilities------------

	//up to 2 levels
	public  void handleMenu(By parentLocator, By childLocator) {
		action.moveToElement(getElement(parentLocator)).perform();
		doClick(childLocator);
	}
	
	//up to 4 levels
	public  void handleMultipleMenuItems(By menu1,By menu2,By menu3 , By menu4) throws InterruptedException {
		doClick(menu1);
		action.moveToElement(getElement(menu2)).perform();
		action.moveToElement(getElement(menu3)).perform();
		doClick(menu4);
	}
	
	public void doActionsClick(By locator) {
		action.click(getElement(locator)).perform();
	}
	
	public void doActionsSendKeys(By locator,String value) {
		action.sendKeys(getElement(locator), value).perform();
	}
	
	public  void sendKeysWithPause(By locator,String valueToEnter,long pauseTime) { //pause() uses only long
		char[] value = valueToEnter.toCharArray(); // [N,a,v,e,e,n]
		for(char c : value) {
			action.sendKeys(getElement(locator),String.valueOf(c)) //converting char to string as we need to provide char sequence
							.pause(pauseTime).perform();
	}
}
	//----------explicit wait utils -------->
	//for any click method
	//An expectation for checking an element is visible and enabled such that you can click it.
	public void waitForElementReadyAndClick(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();;
		
	}
	
	public List<WebElement> waitForAllElementsPresence(By locator,long imeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		catch(TimeoutException e) {
			return Collections.emptyList(); // empty list
 		}
	}
	
	public @Nullable List<WebElement> waitForAllElementsVisible(By locator,long imeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(TimeoutException e) {
			return Collections.emptyList(); // empty list
 		}
	}
	
	public  WebElement waitForElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public  WebElement waitForVisibilityOfElement(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public  WebElement waitForVisibilityOfElement(By locator, long timeOut,long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 wait
		 .pollingEvery(Duration.ofSeconds(pollingTime))
			.ignoring(NoSuchElementException.class)
			.withMessage("Element is not found");
		 return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public  WebElement waitForVisibilityOfElementWithFluentWait(By locator, long timeOut, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)	
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("Element is not found");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	//titleContains -  partial title
	public  String waitForTitleContains(String partialTitle,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.titleContains(partialTitle));
		return driver.getTitle();
		}
		catch (TimeoutException e) {
			System.out.println(partialTitle + " is not found " );
			return null;
		}
	}
	
	// titleIs - exact title
	public  String waitForTitleIs(String title,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.titleIs(title));
		return driver.getTitle();
		}
		catch (TimeoutException e) {
			System.out.println(title + " is not found " );
			return null;
		}
	}
	
	public  String waitForURLContains(String urlValue,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.urlContains(urlValue));
		}
		catch (TimeoutException e) {
			System.out.println(urlValue + " is not found " );
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}
	
	public  String waitForURLToBe(String urlValue,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		wait.until(ExpectedConditions.urlToBe(urlValue));
		}
		catch (TimeoutException e) {
			System.out.println(urlValue + " is not found " );
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}
 
	private Alert waitForAlert(long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return  wait.until(ExpectedConditions.alertIsPresent());
	}
	public  String waitForJSAlertAndAccept(long timeOut ) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;
	}
	
	public  String waitForJSAlertAndDismiss(long timeOut ) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.dismiss();
		return text;
	}
	
	public  String waitForJSPromptAlertAndEnterValue(String value,long timeOut ) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.sendKeys(value);
		alert.accept();
		return text;
	}
	
	public  void waitForFrameAndSwitchToIt(By locator,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	public  void waitForFrameAndSwitchToIt(int frameIndex,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public  void waitForFrameAndSwitchToIt(String frameIdorName,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdorName));
	}
	
	public  void waitForFrameAndSwitchToIt(WebElement frameElement,long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}


}
