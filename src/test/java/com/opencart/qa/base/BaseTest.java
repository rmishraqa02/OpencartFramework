package com.opencart.qa.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.factory.DriverFactory;
import com.opencart.qa.listeners.TestAllureListener;
import com.opencart.qa.pages.HomePage;
import com.opencart.qa.pages.LoginPage;
import com.opencart.qa.pages.ProductInfoPage;
import com.opencart.qa.pages.RegisterPage;
import com.opencart.qa.pages.ResultsPage;

@Listeners({ChainTestListener.class,TestAllureListener.class})
public class BaseTest {

	protected WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	@Parameters({"browser"}) //browser from xml
	@BeforeTest
	public void setUp(@Optional("chrome")  String browserName) { 
		//@Optional browser name is optional here, it wont read from xml, testng will pass normal value or null if nothing is specified
		//@Optional("firefox") -> the default value will be passed to browser name
		df = new DriverFactory();
		prop = df.initProp();
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop); //call by reference
		loginPage = new LoginPage(driver);
	}
	
	@AfterMethod // will be running after each @test method
	public void attachScreenshot(ITestResult result) {
		
		if (!result.isSuccess()) {// only for failure test cases -- true
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
		//------------ take screenshot everytime
		//ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");

	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
