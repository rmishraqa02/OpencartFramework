package com.opencart.qa.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.opencart.qa.exceptions.BrowserException;
import com.opencart.qa.exceptions.FrameworkException;

public class DriverFactory {

		WebDriver driver;
		Properties prop;
		
		public static String highlight;
		public OptionsManager optionsManager;
		
		public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
		
		/**
		 * This method is used to init the driver on basis of given browserName
		 * 
		 * @param browserName
		 * @return it returns the driver value
		 */
		public WebDriver initDriver(Properties prop) {
			String browserName = prop.getProperty("browser");
			System.out.println("Browser Name: " + browserName);
			
			highlight  = prop.getProperty("highlight");
			optionsManager = new OptionsManager(prop);
			switch(browserName.trim().toLowerCase()) {
			case "chrome":
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); //this chrome driver won't be shared with any other thread
				break;
			case "firefox":
				tlDriver.set(new FirefoxDriver(optionsManager.getCFirefoxeOptions()));
				break;
			case "edge":
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;	
			default:
				System.out.println("=======invalid browser======>");
				throw new BrowserException("=====Invalid Browser=====");
			}
			
			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();
			getDriver().get(prop.getProperty("url"));
			return getDriver();
		}
		
		/***
		 * This method is used to initialize properties file
		 * 
		 * @return it returns properties class object
		 */
		
		/***
		 * 
		 * this will return one local copy of driver for a specific thread
		 * @return 
		 * 
		 */
		
		public static WebDriver getDriver() {
			return tlDriver.get();
		}
		
		public Properties initProp(){
			FileInputStream ip  = null;
			prop = new Properties();
			String envtName =	System.getProperty("env"); //to be called from command line
			System.out.println("Env name is " + envtName);
			try {
			if(envtName == null) {
				System.out.println("Envt is null, hence running test cases on qa envt");
				ip = new FileInputStream("./src/test/resource/conifg/config.qa.properties");
			}
			else{
			switch(envtName.trim().toLowerCase()) {
			case "qa":
				ip = new FileInputStream("./src/test/resource/conifg/config.qa.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resource/conifg/config.dev.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resource/conifg/config.stage.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resource/conifg/config.uat.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resource/conifg/config.properties");
				break;
				
			default:
					System.out.println("======invalid env name" + envtName);
					throw new FrameworkException("Invalid envt. name " + envtName);
			}
			}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prop;
		
		}
		
		public static File getScreenshotFile() {
			return ((TakesScreenshot) getDriver()).getScreenshotAs((OutputType.FILE));// temp dir
		}

		public static byte[] getScreenshotByte() {
			return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

		}

		public static String getScreenshotBase64() {
			return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

		}
}
